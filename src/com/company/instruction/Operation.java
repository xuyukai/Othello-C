package com.company.instruction;

public enum Operation {
    NOP,//啥也不做
    BIPUSH,//单字节byte升至int后压入栈
    IPUSH,//int压栈

    POP,//栈内存释放一个slot
    POP2,//栈内存释放两个slot
    POPN,//popn count(4)

    DUP,//栈内存拷贝
    DUP2,//复制栈顶两个slot入栈

    LOADC,//常量加载 loadc index(2) 加载从常量池下标为index的值
    LOADA,//栈地址加载
    NEW,//堆内存申请
    SNEW,//栈内存申请
    ILOAD,
    ALOAD,
    DLOAD,
    IALOAD,
    AALOAD,
    DALOAD,
    ISTORE,
    ASTORE,
    DSTORE,
    IASTORE,
    AASTORE,
    DASTORE,
    IADD,
    DADD,
    ISUB,
    DSUB,
    IMUL,
    DMUL,
    IDIV,
    DDIV,
    INEG,
    DNEG,
    ICMP,
    DCMP,
    I2D,
    D2I,
    I2C,
    JMP,
    JE,
    JNE,
    JL,
    JGE,
    JG,
    JLE,
    CALL,
    RET,
    IRET,
    DRET,
    ARET,
    IPRINT,
    DPRINT,
    CPRINT,
    SPRINT,
    PRINTL,
    ISCAN,
    DSCAN,
    CSCAN
}