package com.company;

import com.company.analyser.Analyser;
import com.company.instruction.Instruction;
import com.company.myException.EndOfCompilationException;
import com.company.myException.EndOfStatementException;
import com.company.token.Token;
import com.company.token.Tokenizer;

import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // write your code here
        boolean isS=false;
        boolean isC=false;
        String outName;
        File fileout = null;
        /*if(args.length==0){
            System.out.println("There are no commandline arguments passed!");
            return;
        }
        if(args.length==1){
            help();
            return;
        }
        else if(args.length>4){
            System.out.println("Wrong number of arguments, please use \"-h\" to get help.");
            return;
        }
        else if(args[0].equals("-s")){
            isS=true;
        }
        else if(args[0].equals("-c")){
            isC=true;
        }
        else{
            help();
            return;
        }
        File filein = new File(args[1]);
        if(args.length>=3&&!args[2].equals("-o")){
            System.out.println("Invalid Arguments, please use \"-h\" to get help.");
            return ;
        }
        if(args.length==4){
            outName=args[3];
            fileout = new File(args[3]);
        }
        else{
            outName="out";
            fileout=new File("out");
        }
*/
        File filein=new File("test.c0");
        fileout=new File("out.s");
        outName="out.s";
        try {
            boolean result = fileout.createNewFile();
        } catch (IOException e) {
            System.out.println("Fail to create output file.");
        }

        try {
            Tokenizer tokenizer = new Tokenizer(filein);
            ArrayList<Token> tokenList = new ArrayList<>();
            tokenizer.readCode();
            try {
                while (true) {
                    Token token = tokenizer.nextToken();
                    tokenList.add(token);
                }
            } catch (EndOfCompilationException ignored) {

                /*for(Token tk:tokenList){
                    System.out.println(tk.getValue()+" "+tk.getType());
                }*/
            }
            try {
                Instruction instruction=new Instruction(outName);
                Analyser analyser = new Analyser(instruction);
                analyser.setTokens(tokenList);
                analyser.analyseProgram();
                analyser.instruct.print();
            } catch (Exception e) {

                System.out.println(e.toString());
            }
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println(e.toString());
        }
        try{

        }catch (Exception e){
          e.printStackTrace();
        };


    }

    static void help(){
        System.out.println("Usage:\n" +
                "  cc0 [options] input [-o file]\n" +
                "or \n" +
                "  cc0 [-h]\n" +
                "Options:\n" +
                "  -s        将输入的 c0 源代码翻译为文本汇编文件\n" +
                "  -c        将输入的 c0 源代码翻译为二进制目标文件\n" +
                "  -h        显示关于编译器使用的帮助\n" +
                "  -o file   输出到指定的文件 file\n" +
                "\n" +
                "不提供任何参数时，默认为 -h\n" +
                "提供 input 不提供 -o file 时，默认为 -o out");
    }
}
