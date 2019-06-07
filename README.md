# ActiveCore

### Project description

ActiveCore is a framework that demonstrates hardware designing concept based on "Micro-Language IP" (MLIP) cores.

MLIP core is a hardware generator that exposes selective functions of behavior and/or microarchitecture for design-time programming and generates hardware implemenations according to certain microarchitectural template.

Full description of the concept and preliminary version of the project (based on C++/Tcl) is given in my PhD thesis:

* A. Antonov, “Methods and Tools for Computer-Aided Synthesis of Processors Based on Microarchitectural Programmable Hardware Generators,” Ph.D dissertation, ITMO University, Saint-Petersburg, 28.12.2018.

Thesis web page: http://fppo.ifmo.ru/dissertation/?number=63419

### Project structure

Current version of project is implemented as several Kotlin libraries that are separately built using IntelliJ IDEA in the following order:

* hwast - AST constructor for behavioral HW specifications

* rtl - MLIP core for behavioral RTL generation

* cyclix - MLIP core for cycle-oriented processing hardware targeting RTL and HLS flows

* pipex - MLIP core for pipelined structures generation

* core generators

The following demo designs for FPGA are available:

* pss_memsplit - minimalistic uC with one RISC-V core (located at /designs/rtl/pss_memsplit)

* mpss - SoC with multiple RISC-V cores and xbar (located at /designs/rtl/mpss)

Pipex MLIP functionality is demonstrated via 6 RISC-V (RV32I) CPU designs with variable-length pipelines (riscv_1stage-riscv_6stage). RISC-V CPU core generator is located at /designs/activecore/riscv_pipex. Preliminary build of the core and software is required. Demo projects use UART-controllable bus master for reset and initialization (tests for pss_memsplit are launched by /designs/rtl/pss_memsplit/sw/benchmarks/hw_test.py).

For questions, please contact antonov.alex.alex@gmail.com
