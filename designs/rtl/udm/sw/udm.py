# -*- coding:utf-8 -*-
from __future__ import division

import struct
import serial
import os

class udm:
    
    sync_byte       = 0x55
    escape_byte     = 0x5a
    idcode_cmd      = 0x00
    rst_cmd         = 0x80
    nrst_cmd        = 0xc0
    wr_cmd          = 0x81
    rd_cmd          = 0x82
    wr_cmd_noinc    = 0x83
    rd_cmd_noinc    = 0x84
    
    def connect(self, com_num, baudrate):
        self.ser = serial.Serial(com_num, baudrate, 8)   
    
    def con(self, com_num, baudrate):
        self.connect(com_num, baudrate)
    
    def disconnect(self):
        self.ser.close()
        print("Connection dropped")
    
    def discon(self):
        self.disconnect()
    
    def check(self):
        wdata = (struct.pack('B', self.sync_byte))
        wdata = wdata + (struct.pack('B', self.idcode_cmd))
        self.ser.write(wdata)
        rdata = self.ser.read()
        rdata = struct.unpack('B', rdata)
        
        if (rdata[0] == self.sync_byte):
            print("Connection established, response: ", hex(rdata[0]))
        else:
            print("Conection failed, response: ", hex(rdata[0]))
    
    def cc(self, com_num, baudrate):
        self.connect(com_num, baudrate)
        self.check()
    
    def sendbyte(self, databyte):
        if ((databyte == self.sync_byte) or (databyte == self.escape_byte)):
            wdata = (struct.pack('B', self.escape_byte))
            self.ser.write(wdata)
        wdata = (struct.pack('B', databyte))
        self.ser.write(wdata)
    
    def rst(self):
        wdata = (struct.pack('B', self.sync_byte))
        wdata = wdata + (struct.pack('B', self.rst_cmd))
        self.ser.write(wdata)
    
    def nrst(self):
        wdata = (struct.pack('B', self.sync_byte))
        wdata = wdata + (struct.pack('B', self.nrst_cmd))
        self.ser.write(wdata)
    
    def hreset(self):
        self.rst()
        self.nrst()
    
    def sendword(self, dataword):
        self.sendbyte((dataword >> 0) & 0xff)
        self.sendbyte((dataword >> 8) & 0xff)
        self.sendbyte((dataword >> 16) & 0xff)
        self.sendbyte((dataword >> 24) & 0xff)
    
    def wr(self, address, dataword):
        self.sendbyte(self.sync_byte)
        self.sendbyte(self.wr_cmd)
        self.sendword(address)     
        self.sendword(4)
        self.sendword(dataword)
    
    def wrarr(self, address, datawords):
        self.sendbyte(self.sync_byte)
        self.sendbyte(self.wr_cmd)
        self.sendword(address)     
        count = len(datawords) 
        self.sendword(count << 2)
        # write data
        for i in range(count):
            self.sendword(datawords[i])
    
    def clr(self, address, size):
        self.sendbyte(self.sync_byte)
        self.sendbyte(self.wr_cmd)
        self.sendword(address)
        self.sendword(size)
        for i in range(size):
            self.sendbyte(0x00)
    
    def rd(self, address):
        self.ser.flush()
        self.sendbyte(self.sync_byte)
        self.sendbyte(self.rd_cmd)
        self.sendword(address)
        self.sendword(4)
        rdata = self.ser.read(4)
        rdata = struct.unpack("BBBB", rdata)
        rdataword = rdata[0] + (rdata[1] << 8) + (rdata[2] << 16) + (rdata[3] << 24)
        return rdataword    
    
    def rdarr32(self, address, length):
        self.ser.flush()
        self.sendbyte(self.sync_byte)
        self.sendbyte(self.rd_cmd)
        self.sendword(address)
        self.sendword(length << 2)
        rdatawords = []
        for i in range(length):
            rdata = self.ser.read(4)
            rdata = struct.unpack("BBBB", rdata)
            rdataword = rdata[0] + (rdata[1] << 8) + (rdata[2] << 16) + (rdata[3] << 24)
            rdatawords.append(rdataword)
        return rdatawords
    
    def wrfile_le(self, address, filename):
        f = open(filename, "rb")
        self.sendbyte(self.sync_byte)
        self.sendbyte(self.wr_cmd)
        # address
        self.sendword(address)
        #length
        length = os.path.getsize(filename)
        self.sendword(length)
        try:
            while True:            
                dbuf0 = f.read(1)
                dbuf1 = f.read(1)
                dbuf2 = f.read(1)
                dbuf3 = f.read(1)
                if dbuf0:
                    dbuf0 = struct.unpack("B", dbuf0)
                    self.sendbyte(dbuf0[0])
                else:
                    break
                if dbuf1:
                    dbuf1 = struct.unpack("B", dbuf1)                    
                    self.sendbyte(dbuf1[0])
                else:
                    break
                if dbuf2:
                    dbuf2 = struct.unpack("B", dbuf2)                    
                    self.sendbyte(dbuf2[0])
                else:
                    break
                if dbuf3:
                    dbuf3 = struct.unpack("B", dbuf3)
                    self.sendbyte(dbuf3[0])
                else:
                    break      
        finally:
            f.close()
    
    def loadbin(self, filename):
        self.rst()
        self.wrfile_le(0x0, filename)
        self.nrst()
    
    
udm = udm()

