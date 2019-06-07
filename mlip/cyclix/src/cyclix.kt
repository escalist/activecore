/*
 * cyclix.kt
 *
 *  Created on: 05.06.2019
 *      Author: Alexander Antonov <antonov.alex.alex@gmail.com>
 *     License: See LICENSE file for details
 */

package cyclix

import hwast.*
import java.lang.Exception
import java.io.File

val OP_CYCPROC = hw_opcode("cycproc")

open class module(name_in : String) : hw_astc() {

    val name = name_in

    var wrvars = mutableMapOf<String, hw_var>()
    var rdvars = mutableMapOf<String, hw_var>()

    var Ports = ArrayList<hw_port>()
    var globals = ArrayList<hw_var>()
    var locals = ArrayList<hw_var>()

    var fifo_ifs = mutableMapOf<String, hw_structvar>()
    var fifo_ins = ArrayList<hw_fifo_in>()
    var fifo_outs = ArrayList<hw_fifo_out>()

    var proc = hw_exec(OP_CYCPROC)

    init {
        this.add(proc)
    }

    private fun add_local(new_local : hw_var) {
        if (wrvars.put(new_local.name, new_local) != null) {
            ERROR("local addition problem!")
        }
        if (rdvars.put(new_local.name, new_local) != null) {
            ERROR("local addition problem!")
        }
        locals.add(new_local)
        new_local.default_astc = this
    }

    fun local(name : String, VarType : VAR_TYPE, src_struct_in : hw_struct, dimensions : hw_dim_static, defval : String) : hw_var {
        var ret_var = hw_var(name, VarType, src_struct_in, dimensions, defval)
        add_local(ret_var)
        return ret_var
    }

    fun local(name : String, VarType : VAR_TYPE, dimensions : hw_dim_static, defval : String) : hw_var {
        var ret_var = hw_var(name, VarType, dimensions, defval)
        add_local(ret_var)
        return ret_var
    }

    fun local(name : String, VarType : VAR_TYPE, msb: Int, lsb: Int, defval : String) : hw_var {
        var ret_var = hw_var(name, VarType, msb, lsb, defval)
        add_local(ret_var)
        return ret_var
    }

    fun local(name : String, VarType : VAR_TYPE, defval : String) : hw_var {
        var ret_var = hw_var(name, VarType, defval)
        add_local(ret_var)
        return ret_var
    }

    fun local(name : String, src_struct_in : hw_struct ,dimensions : hw_dim_static) : hw_var {
        var ret_var = hw_var(name, src_struct_in, dimensions)
        add_local(ret_var)
        return ret_var
    }

    fun local(name : String, src_struct_in : hw_struct) : hw_var {
        var ret_var = hw_var(name, src_struct_in)
        add_local(ret_var)
        return ret_var
    }

    fun ulocal(name : String, dimensions : hw_dim_static, defval : String) : hw_var {
        var ret_var = hw_var(name, VAR_TYPE.UNSIGNED, dimensions, defval)
        add_local(ret_var)
        return ret_var
    }

    fun ulocal(name : String, msb: Int, lsb: Int, defval : String) : hw_var {
        var ret_var = hw_var(name, VAR_TYPE.UNSIGNED, msb, lsb, defval)
        add_local(ret_var)
        return ret_var
    }

    fun ulocal(name : String, defval : String) : hw_var {
        var ret_var = hw_var(name, VAR_TYPE.UNSIGNED, defval)
        add_local(ret_var)
        return ret_var
    }

    fun slocal(name : String, dimensions : hw_dim_static, defval : String) : hw_var {
        var ret_var = hw_var(name, VAR_TYPE.SIGNED, dimensions, defval)
        add_local(ret_var)
        return ret_var
    }

    fun slocal(name : String, msb: Int, lsb: Int, defval : String) : hw_var {
        var ret_var = hw_var(name, VAR_TYPE.SIGNED, msb, lsb, defval)
        add_local(ret_var)
        return ret_var
    }

    fun slocal(name : String, defval : String) : hw_var {
        var ret_var = hw_var(name, VAR_TYPE.SIGNED, defval)
        add_local(ret_var)
        return ret_var
    }

    private fun add_global(new_global : hw_var) {
        if (wrvars.put(new_global.name, new_global) != null) {
            ERROR("global addition problem!")
        }
        if (rdvars.put(new_global.name, new_global) != null) {
            ERROR("global addition problem!")
        }
        globals.add(new_global)
        new_global.default_astc = this
    }

    fun global(name : String, VarType : VAR_TYPE, src_struct_in : hw_struct, dimensions : hw_dim_static, defval : String) : hw_var {
        var ret_var = hw_var(name, VarType, src_struct_in, dimensions, defval)
        add_global(ret_var)
        return ret_var
    }

    fun global(name : String, VarType : VAR_TYPE, dimensions : hw_dim_static, defval : String) : hw_var {
        var ret_var = hw_var(name, VarType, dimensions, defval)
        add_global(ret_var)
        return ret_var
    }

    fun global(name : String, VarType : VAR_TYPE, msb: Int, lsb: Int, defval : String) : hw_var {
        var ret_var = hw_var(name, VarType, msb, lsb, defval)
        add_global(ret_var)
        return ret_var
    }

    fun global(name : String, VarType : VAR_TYPE, defval : String) : hw_var {
        var ret_var = hw_var(name, VarType, defval)
        add_global(ret_var)
        return ret_var
    }

    fun global(name : String, src_struct_in : hw_struct ,dimensions : hw_dim_static) : hw_var {
        var ret_var = hw_var(name, src_struct_in, dimensions)
        add_global(ret_var)
        return ret_var
    }

    fun global(name : String, src_struct_in : hw_struct) : hw_var {
        var ret_var = hw_var(name, src_struct_in)
        add_global(ret_var)
        return ret_var
    }

    fun uglobal(name : String, dimensions : hw_dim_static, defval : String) : hw_var {
        var ret_var = hw_var(name, VAR_TYPE.UNSIGNED, dimensions, defval)
        add_global(ret_var)
        return ret_var
    }

    fun uglobal(name : String, msb: Int, lsb: Int, defval : String) : hw_var {
        var ret_var = hw_var(name, VAR_TYPE.UNSIGNED, msb, lsb, defval)
        add_global(ret_var)
        return ret_var
    }

    fun uglobal(name : String, defval : String) : hw_var {
        var ret_var = hw_var(name, VAR_TYPE.UNSIGNED, defval)
        add_global(ret_var)
        return ret_var
    }

    fun sglobal(name : String, dimensions : hw_dim_static, defval : String) : hw_var {
        var ret_var = hw_var(name, VAR_TYPE.SIGNED, dimensions, defval)
        add_global(ret_var)
        return ret_var
    }

    fun sglobal(name : String, msb: Int, lsb: Int, defval : String) : hw_var {
        var ret_var = hw_var(name, VAR_TYPE.SIGNED, msb, lsb, defval)
        add_global(ret_var)
        return ret_var
    }

    fun sglobal(name : String, defval : String) : hw_var {
        var ret_var = hw_var(name, VAR_TYPE.SIGNED, defval)
        add_global(ret_var)
        return ret_var
    }

    private fun add_port(new_port : hw_port) {
        if (new_port.port_dir != PORT_DIR.IN) {
            if (wrvars.put(new_port.name, new_port) != null) {
                ERROR("Port addition problem!")
            }
            new_port.write_done = true
        }
        if (rdvars.put(new_port.name, new_port) != null) {
            ERROR("Port addition problem!")
        }
        Ports.add(new_port)
        new_port.default_astc = this
    }

    fun port(name : String, port_dir : PORT_DIR, VarType : VAR_TYPE, dimensions : hw_dim_static, defval : String) : hw_port {
        var ret_var = hw_port(name, port_dir, VarType, dimensions, defval)
        add_port(ret_var)
        return ret_var
    }

    fun port(name : String, port_dir : PORT_DIR, VarType : VAR_TYPE, msb: Int, lsb: Int, defval : String) : hw_port {
        var ret_var = hw_port(name, port_dir, VarType, msb, lsb, defval)
        add_port(ret_var)
        return ret_var
    }

    fun port(name : String, port_dir : PORT_DIR, VarType : VAR_TYPE, defval : String) : hw_port {
        var ret_var = hw_port(name, port_dir, VarType, defval)
        add_port(ret_var)
        return ret_var
    }

    fun port(name : String, port_dir : PORT_DIR, src_struct_in : hw_struct, dimensions : hw_dim_static) : hw_port {
        var ret_var = hw_port(name, port_dir, src_struct_in, dimensions)
        add_port(ret_var)
        return ret_var
    }

    fun port(name : String, port_dir : PORT_DIR, src_struct_in : hw_struct) : hw_port {
        var ret_var = hw_port(name, port_dir, src_struct_in)
        add_port(ret_var)
        return ret_var
    }

    fun uport(name : String, port_dir : PORT_DIR, dimensions : hw_dim_static, defval : String) : hw_port {
        var ret_var = hw_port(name, port_dir, VAR_TYPE.UNSIGNED, dimensions, defval)
        add_port(ret_var)
        return ret_var
    }

    fun uport(name : String, port_dir : PORT_DIR, msb: Int, lsb: Int, defval : String) : hw_port {
        var ret_var = hw_port(name, port_dir, VAR_TYPE.UNSIGNED, msb, lsb, defval)
        add_port(ret_var)
        return ret_var
    }

    fun uport(name : String, port_dir : PORT_DIR, defval : String) : hw_port {
        var ret_var = hw_port(name, port_dir, VAR_TYPE.UNSIGNED, defval)
        add_port(ret_var)
        return ret_var
    }

    fun sport(name : String, port_dir : PORT_DIR, dimensions : hw_dim_static, defval : String) : hw_port {
        var ret_var = hw_port(name, port_dir, VAR_TYPE.SIGNED, dimensions, defval)
        add_port(ret_var)
        return ret_var
    }

    fun sport(name : String, port_dir : PORT_DIR, msb: Int, lsb: Int, defval : String) : hw_port {
        var ret_var = hw_port(name, port_dir, VAR_TYPE.SIGNED, msb, lsb, defval)
        add_port(ret_var)
        return ret_var
    }

    fun sport(name : String, port_dir : PORT_DIR, defval : String) : hw_port {
        var ret_var = hw_port(name, port_dir, VAR_TYPE.SIGNED, defval)
        add_port(ret_var)
        return ret_var
    }

    fun input(name : String, VarType : VAR_TYPE, dimensions : hw_dim_static, defval : String) : hw_port {
        var ret_var = hw_port(name, PORT_DIR.IN, VarType, dimensions, defval)
        add_port(ret_var)
        return ret_var
    }

    fun input(name : String, VarType : VAR_TYPE, msb: Int, lsb: Int, defval : String) : hw_port {
        var ret_var = hw_port(name, PORT_DIR.IN, VarType, msb, lsb, defval)
        add_port(ret_var)
        return ret_var
    }

    fun input(name : String, VarType : VAR_TYPE, defval : String) : hw_port {
        var ret_var = hw_port(name, PORT_DIR.IN, VarType, defval)
        add_port(ret_var)
        return ret_var
    }

    fun input(name : String, src_struct_in : hw_struct ,dimensions : hw_dim_static) : hw_port {
        var ret_var = hw_port(name, PORT_DIR.IN, src_struct_in, dimensions)
        add_port(ret_var)
        return ret_var
    }

    fun input(name : String, src_struct_in : hw_struct) : hw_port {
        var ret_var = hw_port(name, PORT_DIR.IN, src_struct_in)
        add_port(ret_var)
        return ret_var
    }

    fun uinput(name : String, dimensions : hw_dim_static, defval : String) : hw_port {
        var ret_var = hw_port(name, PORT_DIR.IN, VAR_TYPE.UNSIGNED, dimensions, defval)
        add_port(ret_var)
        return ret_var
    }

    fun uinput(name : String, msb: Int, lsb: Int, defval : String) : hw_port {
        var ret_var = hw_port(name, PORT_DIR.IN, VAR_TYPE.UNSIGNED, msb, lsb, defval)
        add_port(ret_var)
        return ret_var
    }

    fun uinput(name : String, defval : String) : hw_port {
        var ret_var = hw_port(name, PORT_DIR.IN, VAR_TYPE.UNSIGNED, defval)
        add_port(ret_var)
        return ret_var
    }

    fun sinput(name : String, dimensions : hw_dim_static, defval : String) : hw_port {
        var ret_var = hw_port(name, PORT_DIR.IN, VAR_TYPE.SIGNED, dimensions, defval)
        add_port(ret_var)
        return ret_var
    }

    fun sinput(name : String, msb: Int, lsb: Int, defval : String) : hw_port {
        var ret_var = hw_port(name, PORT_DIR.IN, VAR_TYPE.SIGNED, msb, lsb, defval)
        add_port(ret_var)
        return ret_var
    }

    fun sinput(name : String, defval : String) : hw_port {
        var ret_var = hw_port(name, PORT_DIR.IN, VAR_TYPE.SIGNED, defval)
        add_port(ret_var)
        return ret_var
    }

    fun output(name : String, VarType : VAR_TYPE, dimensions : hw_dim_static, defval : String) : hw_port {
        var ret_var = hw_port(name, PORT_DIR.OUT, VarType, dimensions, defval)
        add_port(ret_var)
        return ret_var
    }

    fun output(name : String, VarType : VAR_TYPE, msb: Int, lsb: Int, defval : String) : hw_port {
        var ret_var = hw_port(name, PORT_DIR.OUT, VarType, msb, lsb, defval)
        add_port(ret_var)
        return ret_var
    }

    fun output(name : String, VarType : VAR_TYPE, defval : String) : hw_port {
        var ret_var = hw_port(name, PORT_DIR.OUT, VarType, defval)
        add_port(ret_var)
        return ret_var
    }

    fun output(name : String, src_struct_in : hw_struct ,dimensions : hw_dim_static) : hw_port {
        var ret_var = hw_port(name, PORT_DIR.OUT, src_struct_in, dimensions)
        add_port(ret_var)
        return ret_var
    }

    fun output(name : String, src_struct_in : hw_struct) : hw_port {
        var ret_var = hw_port(name, PORT_DIR.OUT, src_struct_in)
        add_port(ret_var)
        return ret_var
    }

    fun uoutput(name : String, dimensions : hw_dim_static, defval : String) : hw_port {
        var ret_var = hw_port(name, PORT_DIR.OUT, VAR_TYPE.UNSIGNED, dimensions, defval)
        add_port(ret_var)
        return ret_var
    }

    fun uoutput(name : String, msb: Int, lsb: Int, defval : String) : hw_port {
        var ret_var = hw_port(name, PORT_DIR.OUT, VAR_TYPE.UNSIGNED, msb, lsb, defval)
        add_port(ret_var)
        return ret_var
    }

    fun uoutput(name : String, defval : String) : hw_port {
        var ret_var = hw_port(name, PORT_DIR.OUT, VAR_TYPE.UNSIGNED, defval)
        add_port(ret_var)
        return ret_var
    }

    fun soutput(name : String, dimensions : hw_dim_static, defval : String) : hw_port {
        var ret_var = hw_port(name, PORT_DIR.OUT, VAR_TYPE.SIGNED, dimensions, defval)
        add_port(ret_var)
        return ret_var
    }

    fun soutput(name : String, msb: Int, lsb: Int, defval : String) : hw_port {
        var ret_var = hw_port(name, PORT_DIR.OUT, VAR_TYPE.SIGNED, msb, lsb, defval)
        add_port(ret_var)
        return ret_var
    }

    fun soutput(name : String, defval : String) : hw_port {
        var ret_var = hw_port(name, PORT_DIR.OUT, VAR_TYPE.SIGNED, defval)
        add_port(ret_var)
        return ret_var
    }

    private fun add_fifo_in(new_fifo_in: hw_fifo_in) {
        if (fifo_ifs.put(new_fifo_in.name, new_fifo_in) != null) {
            ERROR("FIFO addition problem!")
        }
        fifo_ins.add(new_fifo_in)
    }

    fun fifo_in(name : String, VarType : VAR_TYPE, src_struct_in : hw_struct, dimensions : hw_dim_static) : hw_fifo_in {
        var ret_var = hw_fifo_in(name, VarType, src_struct_in, dimensions)
        add_fifo_in(ret_var)
        return ret_var
    }

    fun fifo_in(name : String, VarType : VAR_TYPE, dimensions : hw_dim_static) : hw_fifo_in {
        var ret_var = hw_fifo_in(name, VarType, dimensions)
        add_fifo_in(ret_var)
        return ret_var
    }

    fun fifo_in(name : String, VarType : VAR_TYPE, msb: Int, lsb: Int) : hw_fifo_in {
        var ret_var = hw_fifo_in(name, VarType, msb, lsb)
        add_fifo_in(ret_var)
        return ret_var
    }

    fun fifo_in(name : String, src_struct_in : hw_struct ,dimensions : hw_dim_static) : hw_fifo_in {
        var ret_var = hw_fifo_in(name, src_struct_in, dimensions)
        add_fifo_in(ret_var)
        return ret_var
    }

    fun fifo_in(name : String, src_struct_in : hw_struct) : hw_fifo_in {
        var ret_var = hw_fifo_in(name, src_struct_in)
        add_fifo_in(ret_var)
        return ret_var
    }

    fun ufifo_in(name : String, dimensions : hw_dim_static) : hw_fifo_in {
        var ret_var = hw_fifo_in(name, VAR_TYPE.UNSIGNED, dimensions)
        add_fifo_in(ret_var)
        return ret_var
    }

    fun ufifo_in(name : String, msb: Int, lsb: Int) : hw_fifo_in {
        var ret_var = hw_fifo_in(name, VAR_TYPE.UNSIGNED, msb, lsb)
        add_fifo_in(ret_var)
        return ret_var
    }

    fun sfifo_in(name : String, dimensions : hw_dim_static) : hw_fifo_in {
        var ret_var = hw_fifo_in(name, VAR_TYPE.SIGNED, dimensions)
        add_fifo_in(ret_var)
        return ret_var
    }

    fun sfifo_in(name : String, msb: Int, lsb: Int) : hw_fifo_in {
        var ret_var = hw_fifo_in(name, VAR_TYPE.SIGNED, msb, lsb)
        add_fifo_in(ret_var)
        return ret_var
    }

    fun add_fifo_out(new_fifo_out: hw_fifo_out) {
        if (fifo_ifs.put(new_fifo_out.name, new_fifo_out) != null) {
            ERROR("FIFO addition problem!")
        }
        fifo_outs.add(new_fifo_out)
    }

    fun fifo_out(name : String, VarType : VAR_TYPE, src_struct_in : hw_struct, dimensions : hw_dim_static) : hw_fifo_out {
        var ret_var = hw_fifo_out(name, VarType, src_struct_in, dimensions)
        add_fifo_out(ret_var)
        return ret_var
    }

    fun fifo_out(name : String, VarType : VAR_TYPE, dimensions : hw_dim_static) : hw_fifo_out {
        var ret_var = hw_fifo_out(name, VarType, dimensions)
        add_fifo_out(ret_var)
        return ret_var
    }

    fun fifo_out(name : String, VarType : VAR_TYPE, msb: Int, lsb: Int) : hw_fifo_out {
        var ret_var = hw_fifo_out(name, VarType, msb, lsb)
        add_fifo_out(ret_var)
        return ret_var
    }

    fun fifo_out(name : String, src_struct_in : hw_struct ,dimensions : hw_dim_static) : hw_fifo_out {
        var ret_var = hw_fifo_out(name, src_struct_in, dimensions)
        add_fifo_out(ret_var)
        return ret_var
    }

    fun fifo_out(name : String, src_struct_in : hw_struct) : hw_fifo_out {
        var ret_var = hw_fifo_out(name, src_struct_in)
        add_fifo_out(ret_var)
        return ret_var
    }

    fun ufifo_out(name : String, dimensions : hw_dim_static) : hw_fifo_out {
        var ret_var = hw_fifo_out(name, VAR_TYPE.UNSIGNED, dimensions)
        add_fifo_out(ret_var)
        return ret_var
    }

    fun ufifo_out(name : String, msb: Int, lsb: Int) : hw_fifo_out {
        var ret_var = hw_fifo_out(name, VAR_TYPE.UNSIGNED, msb, lsb)
        add_fifo_out(ret_var)
        return ret_var
    }

    fun sfifo_out(name : String, dimensions : hw_dim_static) : hw_fifo_out {
        var ret_var = hw_fifo_out(name, VAR_TYPE.SIGNED, dimensions)
        add_fifo_out(ret_var)
        return ret_var
    }

    fun sfifo_out(name : String, msb: Int, lsb: Int) : hw_fifo_out {
        var ret_var = hw_fifo_out(name, VAR_TYPE.SIGNED, msb, lsb)
        add_fifo_out(ret_var)
        return ret_var
    }

    fun fifo_wr(fifo : hw_fifo_out, wdata : hw_param) : hw_var {
        var new_expr = hw_exec_fifo_wr(fifo)
        var genvar = hw_var(GetGenName("fifo_rdy"), VAR_TYPE.UNSIGNED, 0, 0, "0")
        new_expr.AddRdParam(wdata)
        new_expr.AddWrVar(genvar)
        new_expr.AddGenVar(genvar)
        AddExpr(new_expr)
        return genvar
    }

    fun fifo_rd(fifo : hw_fifo_in, rdata : hw_var) : hw_var {
        var new_expr = hw_exec_fifo_rd(fifo)
        var genvar = hw_var(GetGenName("fifo_rdy"), VAR_TYPE.UNSIGNED, 0, 0, "0")
        new_expr.AddWrVar(genvar)
        new_expr.AddGenVar(genvar)
        new_expr.AddWrVar(rdata)
        AddExpr(new_expr)
        return genvar
    }

    fun export_to_rtl() : rtl.module {

        println("###########################################")
        println("#### Starting Cyclix-to-RTL generation ####")
        println("###########################################")

        // TODO: pre-validation

        var rtl_generator = RtlGenerator(this)
        var rtl_gen = rtl_generator.generate()

        // TODO: post-validation

        println("############################################")
        println("#### Cyclix-to-RTL generation complete! ####")
        println("############################################")

        return rtl_gen
    }

    fun export_to_vivado_cpp(pathname : String) {

        println("############################################")
        println("#### Cyclix: starting vivado_cpp export ####")
        println("############################################")

        // TODO: validate()

        var writer = VivadoCppWriter(this)
        writer.write(pathname)

        println("#############################################")
        println("#### Cyclix: vivado_cpp export complete! ####")
        println("#############################################")
    }
}