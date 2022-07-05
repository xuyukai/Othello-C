package com.company.instruction;

import java.awt.image.BufferStrategy;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Instruction {
    ArrayList<String> _constants = new ArrayList<>();//常量表
    ArrayList<String> _start = new ArrayList<>();//启动代码，负责执行全局变量的初始化
    ArrayList<String> _functions = new ArrayList<>();//函数表，记录函数的基本信息
    ArrayList<ArrayList<String>> _local=new ArrayList<>();//函数体
    BufferedWriter bw=null;
    public Instruction(String name) throws IOException {
        bw=new BufferedWriter(new FileWriter(name));
    }

    public void addFunc2Constants(String funcName) {
        _constants.add(" S \"" + funcName + "\"");
    }

    public void addVariable2Constants(int value) {
        _constants.add(" I " + value);
    }

    public void addStartInstruct(Operation op,int x1,int x2){
        _start.add(op+" "+String.valueOf(x1)+" "+ x2);
    }

    int getIndexInConstants(String funcName) {
        int i = 0;
        for (i = 0; i < _constants.size(); i++) {
            String str = _constants.get(i);
            String[] array = str.split(" ");
            if (array[2].contains(funcName)) {
                return i;
            }
        }
        return -1;
    }

    public void addLocalInstruct(int index,Operation op,int x1,int x2){
        String str=op+" "+String.valueOf(x1)+", "+String.valueOf(x2);
        _local.get(index-1).add(str);
    }

    public void addLocalInstruct(int index,Operation op,int x1){
        String str=op+" "+String.valueOf(x1);
        _local.get(index-1).add(str);
    }

    public void addLocalInstruct(int index,Operation op){
        String str=op.toString();
        _local.get(index-1).add(str);
    }

    public void addLocalInstruct(int index,int insertP,Operation op,int x1,int x2){
        String str=op+" "+String.valueOf(x1)+", "+String.valueOf(x2);
        _local.get(index-1).add(insertP+1,str);
    }

    public void addLocalHead(String head){
        ArrayList<String> tmp=new ArrayList<>();
        tmp.add(head);
        _local.add(tmp);
    }

    public void addFunc2Functions(Function fc) {
        _functions.add(" " + getIndexInConstants(fc.funcName) + " " + fc.paraNum + " " + 1);
    }

    public int getPreCodeIndex(int funcInx){
        return _local.get(funcInx).size()-2;
    }

    public void print(){
        try{
            bw.write(".constants:");
            bw.newLine();
            for(int i=0;i<_constants.size();i++){
                String str=String.valueOf(i)+_constants.get(i);
                bw.write(str);
                bw.newLine();
            }
            bw.write(".start:");
            bw.newLine();
            for(int i=0;i<_start.size();i++){
                String str=String.valueOf(i)+"\t"+_start.get(i);
                bw.write(str);
                //System.out.println(str);
                bw.newLine();
            }
            bw.write(".functions:");
            bw.newLine();
            for(int i=0;i<_functions.size();i++){
                String str=String.valueOf(i)+_functions.get(i);
                bw.write(str);
                //System.out.println(str);
                bw.newLine();
            }
            for(ArrayList<String> arrayList:_local){
                bw.write(arrayList.get(0));
                bw.newLine();
                //System.out.println(arrayList.get(0));
                for(int i=1;i<arrayList.size();i++){
                    String str=String.valueOf(i-1)+"\t"+arrayList.get(i);
                    bw.write(str);
                    //System.out.println(str);
                    bw.newLine();
                }
            }
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
