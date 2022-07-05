package com.company.analyser;

import com.company.instruction.Function;
import com.company.instruction.Instruction;
import com.company.instruction.Operation;
import com.company.instruction.Variable;
import com.company.myException.*;
import com.company.token.Token;
import com.company.token.TokenType;
import com.company.tools.myPair;
import javafx.fxml.LoadException;

import javax.sound.midi.MidiEvent;
import java.util.ArrayList;

public class Analyser {
    public final Instruction instruct;
    int funcID = 0;
    boolean isVoidFunc = true;
    String funcName="";
    boolean isGlobal = true;
    boolean isConst = false;
    boolean hasMain=false;
    boolean hasReturn=false;
    int parameters=0;
    int jmpType=0;
    ArrayList<Token> tokens = new ArrayList<>();
    ArrayList<Variable> uninitialized_vars = new ArrayList<>();
    ArrayList<Variable> vars = new ArrayList<>();
    ArrayList<Variable> consts = new ArrayList<>();
    ArrayList<Function> functions = new ArrayList<>();
    ArrayList<String> localVar=new ArrayList<>();
    ArrayList<String> localPara=new ArrayList<>();
    ArrayList<String> global=new ArrayList<>();

    int offset;
    myPair<Integer, Integer> currentPos;

    public Analyser(Instruction instruction) {
        this.instruct=instruction;
    }

    //<c0-program>={<variable-declaration>}{<function-definition>}
    public void analyseProgram() throws NoPreviousTokenException, NeedTypeException, SyntaxErrorException, AlreadyDeclaredException, NeedIdentifierException, MissingBracketException, MissingSemicolonException, NotDeclaredException, NoChangeConstException, WrongNumberOfParameterException, NeedValueForConstException, NeedMainFunctionException, NeedReturnStatementException, NoNeedOfReturnException {
        //System.out.println("analyseProgram!");
        //<variable-declaration>
        while (true) {
            Token tk = nextToken();
            // System.out.println(tk.getValue());
            if (tk.getType() == TokenType.CONST) {//如果是const，直接就是变量声明
                unreadToken();
                analyseVariableDeclaration();
                //System.out.println("Back from analyseVariableDeclaration");
            } else if (tk.getType() == TokenType.INT) {//如果是int，要看两个后的token是不是括号
                tk = nextToken();//identifier
                //System.out.println(tk.getValue());
                tk = nextToken();//'('
                //System.out.println(tk.getValue());
                if (tk.getType() == TokenType.LEFT_BRACKET_MARK) {//说明是函数定义
                    unreadToken();//不要括号
                    unreadToken();//不要变量名
                    unreadToken();
                    break;
                } else {
                    unreadToken();//不要括号
                    unreadToken();//不要变量名
                    unreadToken();
                    analyseVariableDeclaration();
                   // System.out.println("Back from analyseVariableDeclaration");
                }
            } else if (tk.getType() == TokenType.VOID) {
                tk = nextToken();//identifier
                //System.out.println(tk.getValue());
                tk = nextToken();//'('
                //System.out.println(tk.getValue());
                if (tk.getType() == TokenType.LEFT_BRACKET_MARK) {//说明是函数定义
                    unreadToken();//不要括号
                    unreadToken();//不要变量名
                    unreadToken();
                    break;
                } else {
                    throw new NeedTypeException(currentPos);
                }
            }
        }
        isGlobal = false;
        //<function-definition>
        Token tk;
        while ((tk = nextToken()) != null) {
            if (tk.getType() != TokenType.INT && tk.getType() != TokenType.VOID) {
                unreadToken();
                break;
            }
            unreadToken();
            analyseFunction();
            //System.out.println("Back from analyseFunction");
        }
        //System.out.println("Compile Complete!");
        if(!hasMain){
            throw new NeedMainFunctionException();
        }
    }

    //<function>=<type-specifier><identifier><parameter-clause><compound-statement>
    //Unfinished
    void analyseFunction() throws NeedTypeException, NeedIdentifierException, AlreadyDeclaredException, SyntaxErrorException, NoPreviousTokenException, MissingBracketException, MissingSemicolonException, NotDeclaredException, NoChangeConstException, WrongNumberOfParameterException, NeedValueForConstException, NeedReturnStatementException, NoNeedOfReturnException {
        //System.out.println("analyseFunction!");
        funcID++;
        parameters=0;
        hasReturn=false;
        localVar.clear();
        Token tk = nextToken();
        //<type-specifier>
        if (tk.getType() != TokenType.INT && tk.getType() != TokenType.VOID) {
            throw new NeedTypeException(currentPos);
        }

        //判断到底是void还是int
        isVoidFunc = (tk.getType() != TokenType.INT);

        //<identifier>
        tk = nextToken();
        if (tk.getType() != TokenType.IDENTIFIER) {
            throw new NeedIdentifierException(currentPos);
        } else if (isDeclaredFunc(tk)) {
            throw new AlreadyDeclaredException(currentPos);
        }

        funcName = tk.getValue();
        if(funcName.equals("main"))
            hasMain=true;
        String head="."+funcName+":";
        instruct.addLocalHead(head);
        instruct.addFunc2Constants(funcName);

        //<parameter-clause>
        analyseParameterClause();
        // System.out.println("Back from analyseParameterClause");
        addFunction(new Function(funcName,parameters,isVoidFunc));
        instruct.addFunc2Functions(new Function(funcName,parameters,isVoidFunc));
        //<compound-statement>
        analyseCompoundStatement();
        //System.out.println("Back from analyseCompoundStatement");
        if(!hasReturn&&!isVoidFunc){
            throw new NeedReturnStatementException(funcName);
        }
        if (!hasReturn) {
            instruct.addLocalInstruct(funcID,Operation.RET);
        }

    }

    //<parameter-clause>='('+[<parameter-declaration-list>] +')'
    //Finished
    void analyseParameterClause() throws SyntaxErrorException, NoPreviousTokenException, NeedTypeException, MissingBracketException, NeedIdentifierException {
        //System.out.println("analyseParameterClause!");
        //'('
        Token tk = nextToken();
        if (tk.getType() != TokenType.LEFT_BRACKET_MARK) {
            throw new SyntaxErrorException(currentPos);
        }
        //[<parameter-declaration-list>]
        tk = nextToken();
        if (tk.getType() == TokenType.INT || tk.getType() == TokenType.CONST) {
            unreadToken();
            analyseParameterDecList();
            //  System.out.println("Back from analyseParameterDecList");
        } else {
            unreadToken();
        }

        //')'
        tk = nextToken();
        if (tk.getType() != TokenType.RIGHT_BRACKET_MARK) {
            throw new MissingBracketException(currentPos);
        }
    }

    //<parameter-declaration-list>=<parameter-declaration>{','+<parameter-declaration>}
    //Unfinished
    void analyseParameterDecList() throws NoPreviousTokenException, NeedTypeException, NeedIdentifierException {
        //System.out.println("analyseParameterDecList!");
        //<parameter-declaration>
        analyseParameterDeclaration();
        parameters++;
        //System.out.println("Back from analyseParameterDeclaration");
        //{','+<parameter-declaration>}
        while (true) {
            Token tk = nextToken();
            //','
            if (tk.getType() != TokenType.COMMA_MARK) {
                unreadToken();
                break;
            }
            //<parameter-declaration>
            analyseParameterDeclaration();
            parameters++;
            //System.out.println("Back from analyseParameterDeclaration");

            //todo
            //生成指令

        }
    }

    //<compound-statement> ='{'+ {<variable-declaration>} <statement-seq> '}'
    void analyseCompoundStatement() throws SyntaxErrorException, NeedTypeException, NoPreviousTokenException, MissingBracketException, MissingSemicolonException, NeedIdentifierException, NotDeclaredException, NoChangeConstException, WrongNumberOfParameterException, NeedValueForConstException, AlreadyDeclaredException, NoNeedOfReturnException, NeedReturnStatementException {
        //System.out.println("analyseCompoundStatement!");
        //'{'
        Token tk = nextToken();
        if (tk.getType() != TokenType.LEFT_CURLY_MARK) {
            throw new SyntaxErrorException(currentPos);
        }

        //{<variable-declaration>}
        while (true) {
            tk = nextToken();
            if (tk.getType() != TokenType.CONST && tk.getType() != TokenType.INT) {
                unreadToken();
                break;
            }
            int level = 1;//目前是函数内这一层

            unreadToken();
            analyseVariableDeclaration();
            //System.out.println("Back from analyseVariableDeclaration");
        }

        //<statement-seq>
        analyseStateSeq();
        //System.out.println("Back from analyseStateSeq");

        //System.out.println(tk.getValue());
        //'}'
        tk = nextToken();
        //System.out.println(tk.getValue());
        if (tk.getType() != TokenType.RIGHT_CURLY_MARK) {
            throw new MissingBracketException(currentPos);
        }
    }

    //<statement-seq>={<statement>}
    //BUG
    void analyseStateSeq() throws NoPreviousTokenException, SyntaxErrorException, MissingBracketException, MissingSemicolonException, NeedIdentifierException, NotDeclaredException, NoChangeConstException, WrongNumberOfParameterException,NoNeedOfReturnException,NeedReturnStatementException {
        //System.out.println("analyseStateSeq!");
        while (true) {
            try {
                analyseStatement();
                //System.out.println("Back from analyseStatement");
            } catch (EndOfStatementException  e) {
                break;
            }
        }
    }

    /*<statement> ::='{' <statement-seq> '}'
            |<condition-statement>
            |<loop-statement>
            |<jump-statement>
            |<print-statement>
            |<scan-statement>
            |<assignment-expression>';'
            |<function-call>';'
            |';'
    */
    void analyseStatement() throws NoPreviousTokenException, SyntaxErrorException, MissingBracketException, MissingSemicolonException, NeedIdentifierException, NotDeclaredException, NoChangeConstException, EndOfStatementException, WrongNumberOfParameterException, NoNeedOfReturnException, NeedReturnStatementException {
        //System.out.println("analyseStatement!");
        Token tk = nextToken();
        //System.out.println(tk.getValue());
        switch (tk.getType()) {
            //statement-seq
            case LEFT_CURLY_MARK: {
                analyseStateSeq();
                //System.out.println("Back from analyseStateSeq");
                tk = nextToken();
                if (tk.getType() != TokenType.RIGHT_CURLY_MARK) {
                    throw new MissingBracketException(currentPos);
                }
                break;
            }
            //condition-statement
            case IF: {
                unreadToken();
                analyseConditionState();
                //System.out.println("Back from analyseConditionState");
                break;
            }
            //<loop-statement>
            case WHILE: {
                unreadToken();
                analyseLoopState();
                //System.out.println("Back from analyseLoopState");
                break;
            }
            //<jump-statement>
            case RETURN: {
                unreadToken();
                analyseJumpState();
                //System.out.println("Back from analyseJumpState");
                break;
            }
            //<print-statement>
            case PRINT: {
                unreadToken();
                analysePrintState();
                //System.out.println("Back from analysePrintState");
                break;
            }
            //<scan-statement>
            case SCAN: {
                unreadToken();
                analyseScanState();
                //System.out.println("Back from analyseScanState");
                break;
            }
            //<assignment-expression><function-call>
            case IDENTIFIER: {
                //System.out.println(tk.getValue());
                if (isDeclaredFunc(tk)) {
                    //System.out.println("here");
                    unreadToken();
                    analyseFunctionCall();
                    //System.out.println("Back from analyseFunctionCall");
                    tk = nextToken();
                    if (tk.getType() != TokenType.SEMICOLON_MARK) {
                        throw new MissingSemicolonException(currentPos);
                    }
                    break;
                }
                else if (isDeclared(tk)) {
                    //System.out.println("here");
                    unreadToken();
                    analyseAssignExpres();
                    //System.out.println("Back from analyseAssignExpres");
                    tk = nextToken();
                    if (tk.getType() != TokenType.SEMICOLON_MARK) {
                        throw new MissingSemicolonException(currentPos);
                    }
                    break;
                }
                else {
                    throw new NotDeclaredException(currentPos);
                }
            }
            case SEMICOLON_MARK: {
                break;
            }
            default: {
                unreadToken();
                throw new EndOfStatementException();
            }
        }
        //analyseStatement();
    }

    //todo
    //<assignment-expression> ::= <identifier><assignment-operator><expression>
    private void analyseAssignExpres() throws NeedIdentifierException, SyntaxErrorException, NoChangeConstException, NoPreviousTokenException, MissingBracketException, NotDeclaredException, WrongNumberOfParameterException {
        //System.out.println("analyseAssignExpres!");
        Token tk = nextToken();
        if (tk.getType() != TokenType.IDENTIFIER) {
            throw new NeedIdentifierException(currentPos);
        } else if (isDeclaredFunc(tk)) {
            throw new SyntaxErrorException(currentPos);
        } else if (isConstant(tk)) {
            throw new NoChangeConstException(currentPos);
        }
       // instruct.addLocalInstruct(funcID,Operation.LOADA,0,);
        tk = nextToken();
        if (tk.getType() != TokenType.EQUAL_MARK) {
            throw new SyntaxErrorException(currentPos);
        }
        analyseExpression();
        //指令
    }

    //<function-call> ::=<identifier> '(' [<expression-list>] ')'
    //todo call another function(parameter&instruction)
    private void analyseFunctionCall() throws NeedIdentifierException, SyntaxErrorException, MissingBracketException, NoPreviousTokenException, NotDeclaredException, WrongNumberOfParameterException {
        //System.out.println("analyseFunctionCall!");
        Token tk = nextToken();
        if (tk.getType() != TokenType.IDENTIFIER) {
            throw new NeedIdentifierException(currentPos);
        }
        //如果不是函数的话...
        if (!isDeclaredFunc(tk)) {
            throw new SyntaxErrorException(currentPos);
        }

        Token tmp = tk;//这个是调用的函数名
        Function calledFunc=getFunction(tmp);

        //左括号
        tk = nextToken();
        if (tk.getType() != TokenType.LEFT_BRACKET_MARK) {
            throw new SyntaxErrorException(currentPos);
        }
        //参数
        int paraNum=0;
        tk = nextToken();
        if (tk.getType() != TokenType.RIGHT_BRACKET_MARK) {
            unreadToken();
            paraNum=analyseExpressionList();
        }
        if(paraNum!=calledFunc.getParaNum())
            throw new WrongNumberOfParameterException(currentPos);
        //System.out.println("here");
        tk = nextToken();
        if (tk.getType() != TokenType.RIGHT_BRACKET_MARK) {
            throw new MissingBracketException(currentPos);
        }
        instruct.addLocalInstruct(funcID,Operation.CALL,getFunctionIndex(tmp.getValue()));
    }

    //todo
    //<expression-list> ::=<expression>{','<expression>}
    private int analyseExpressionList() throws NoPreviousTokenException, SyntaxErrorException, MissingBracketException, NeedIdentifierException, NotDeclaredException,WrongNumberOfParameterException {
        //System.out.println("analyseExpressionList!");
        //System.out.println("here");
        analyseExpression();
        int paraNum=1;
        while (true) {
            Token tk = nextToken();
            if (tk.getType() != TokenType.COMMA_MARK) {
                unreadToken();
                break;
            }
            analyseExpression();
            paraNum++;
            //指令
        }
        return paraNum;
    }


    //<print-statement> ::= 'print' '(' [<printable-list>] ')' ';'
    private void analysePrintState() throws SyntaxErrorException, MissingBracketException, NoPreviousTokenException, MissingSemicolonException, NeedIdentifierException,WrongNumberOfParameterException, NotDeclaredException {
        //System.out.println("analysePrintState!");
        Token tk = nextToken();
        if (tk.getType() != TokenType.PRINT) {
            throw new SyntaxErrorException(currentPos);
        }
        tk = nextToken();
        if (tk.getType() != TokenType.LEFT_BRACKET_MARK) {
            throw new MissingBracketException(currentPos);
        }
        tk = nextToken();
        if (tk.getType() != TokenType.RIGHT_BRACKET_MARK) {
            unreadToken();
            analysePrintList();
        }
        unreadToken();
        tk = nextToken();
        if (tk.getType() != TokenType.RIGHT_BRACKET_MARK) {
            throw new MissingBracketException(currentPos);
        }
        tk = nextToken();
        if (tk.getType() != TokenType.SEMICOLON_MARK) {
            throw new MissingSemicolonException(currentPos);
        }
        //todo
        instruct.addLocalInstruct(funcID,Operation.PRINTL,0,0);
    }

    //<jump-statement> ::= <return-statement>
    private void analyseJumpState() throws NoPreviousTokenException, SyntaxErrorException, MissingSemicolonException, MissingBracketException, NeedIdentifierException, WrongNumberOfParameterException, NotDeclaredException, NoNeedOfReturnException, NeedReturnStatementException {
        //System.out.println("analyseJumpState!");
        analyseReturnState();
        //System.out.println("Back from analyseReturnState");
    }

    //<return-statement> ::= 'return' [<expression>] ';'
    private void analyseReturnState() throws SyntaxErrorException, NoPreviousTokenException, MissingSemicolonException, MissingBracketException, NeedIdentifierException, WrongNumberOfParameterException, NotDeclaredException, NoNeedOfReturnException, NeedReturnStatementException, NoNeedOfReturnException {
        // System.out.println("analyseReturnState!");
        //'return'
        Token tk = nextToken();
        if (tk.getType() != TokenType.RETURN) {
            throw new SyntaxErrorException(currentPos);
        }
        //[<expression>]
        tk = nextToken();
        //System.out.println(tk.getValue());
        if (tk.getType() != TokenType.SEMICOLON_MARK) {
            unreadToken();
            analyseExpression();
            //System.out.println("Back from analyseExpression");
            tk = nextToken();
            //';'
            if (tk.getType() != TokenType.SEMICOLON_MARK) {
                throw new MissingSemicolonException(currentPos);
            }
            if(!isVoidFunc){
                instruct.addLocalInstruct(funcID,Operation.IRET);
            }
            else{
                //System.out.println("here");
                throw new NoNeedOfReturnException(currentPos);
            }
        }
        else {
            if(!isVoidFunc){
                throw new NeedReturnStatementException(funcName);
            }
        }

        //';'
        if (tk.getType() != TokenType.SEMICOLON_MARK) {
            throw new MissingSemicolonException(currentPos);
        }
        if(isVoidFunc){
            instruct.addLocalInstruct(funcID,Operation.RET);
        }
        hasReturn=true;
        //todo
    }

    //<scan-statement>  ::= 'scan' '(' <identifier> ')' ';'
    private void analyseScanState() throws SyntaxErrorException, MissingBracketException, NoPreviousTokenException, MissingSemicolonException, NeedIdentifierException, NoChangeConstException {
        //System.out.println("analyseScanState!");
        //'scan'
        Token tk = nextToken();
        if (tk.getType() != TokenType.SCAN) {
            throw new SyntaxErrorException(currentPos);
        }

        /*********************************************************/
        //'('
        tk = nextToken();
        if (tk.getType() != TokenType.LEFT_BRACKET_MARK) {
            throw new MissingBracketException(currentPos);
        }

        /*********************************************************/
        //<identifier>
        tk = nextToken();
        if (tk.getType() != TokenType.IDENTIFIER) {
            throw new NeedIdentifierException(currentPos);
        }
        if (isDeclaredFunc(tk)) {
            throw new NeedIdentifierException(currentPos);
        }
        if (isConstant(tk)) {
            throw new NoChangeConstException(currentPos);
        }
        Token tmp = tk;
        if(isGlobal){
            instruct.addLocalInstruct(funcID,Operation.LOADA,1,getGlobalIndex(tk.getValue()));
        }
        if(getLocalParaIndex(tk.getValue())!=-1){
            instruct.addLocalInstruct(funcID,Operation.LOADA,0,getLocalParaIndex(tk.getValue()));
        }
        if(isDeclared(tk)){
            instruct.addLocalInstruct(funcID,Operation.LOADA,0,getLocalVarIndex(tk.getValue()));
        }

        /*********************************************************/
        //')'
        tk = nextToken();
        if (tk.getType() != TokenType.RIGHT_BRACKET_MARK) {
            throw new MissingBracketException(currentPos);
        }

        /*********************************************************/
        //';'
        tk = nextToken();
        if (tk.getType() != TokenType.SEMICOLON_MARK) {
            throw new MissingSemicolonException(currentPos);
        }
        if (isUninitializedVariable(tmp)) {
            delUninitialized_vars(new Variable(false,isGlobal,tmp.getValue(),funcName));
        }
        //
        instruct.addLocalInstruct(funcID,Operation.ISCAN);
        instruct.addLocalInstruct(funcID,Operation.ISTORE);
    }

    //<printable-list>  ::= <printable> {',' <printable>}
    private void analysePrintList() throws NoPreviousTokenException, SyntaxErrorException, MissingBracketException, NeedIdentifierException, NotDeclaredException, WrongNumberOfParameterException {
        //System.out.println("analysePrintList!");
        analysePrintable();
        while (true) {
            Token tk = nextToken();
            if (tk.getType() != TokenType.COMMA_MARK) {
                break;
            }
            instruct.addLocalInstruct(funcID,Operation.BIPUSH,32,0);
            instruct.addLocalInstruct(funcID,Operation.CPRINT,0,0);
            analysePrintable();

        }

    }

    //<printable>=<expression>
    private void analysePrintable() throws NoPreviousTokenException, SyntaxErrorException, MissingBracketException, NeedIdentifierException, NotDeclaredException, WrongNumberOfParameterException {
        //System.out.println("analysePrintable!");
       // System.out.println("here");
        analyseExpression();
        instruct.addLocalInstruct(funcID,Operation.IPRINT,0,0);
    }

    //<expression>=<additive-expression>
    private void analyseExpression() throws NoPreviousTokenException, SyntaxErrorException, MissingBracketException, NeedIdentifierException, NotDeclaredException, WrongNumberOfParameterException {
        ///System.out.println("ahere");
        analyseAdditiveExpression();
        //System.out.println("Back from analyseAdditiveExpression");
    }

    //<additive-expression>=<multiplicative-expression>{<additive-operator><multiplicative-expression>}
    private void analyseAdditiveExpression() throws NoPreviousTokenException, SyntaxErrorException, MissingBracketException, NeedIdentifierException, NotDeclaredException, WrongNumberOfParameterException {
        //System.out.println("analyseAdditiveExpression!");
        //<multiplicative-expression>
        analyseMultiplicativeExpression();
        //System.out.println("Back from analyseMultiplicativeExpression");
        while (true) {
            Token tk = nextToken();
            //<+>/<->
            if (tk.getType() != TokenType.PLUS_MARK && tk.getType() != TokenType.MINUS_MARK) {
                unreadToken();
                break;
            }
            Token tmp=tk;

            //<multiplicative-expression>
            analyseMultiplicativeExpression();
            //System.out.println("Back from analyseMultiplicativeExpression");

            if(tmp.getType()==TokenType.PLUS_MARK){
                if(isGlobal){
                    instruct.addStartInstruct(Operation.IADD,0,0);
                }
                else
                    instruct.addLocalInstruct(funcID,Operation.IADD,0,0);
            }
            else if(tmp.getType()==TokenType.MINUS_MARK){
                if(isGlobal){
                    instruct.addStartInstruct(Operation.ISUB,0,0);
                }
                else
                    instruct.addLocalInstruct(funcID,Operation.ISUB,0,0);
            }
            //生成指令！！

        }
    }

    //<multiplicative-expression> ::=<unary-expression>{<multiplicative-operator><unary-expression>}
    private void analyseMultiplicativeExpression() throws SyntaxErrorException, NoPreviousTokenException, MissingBracketException, NeedIdentifierException, NotDeclaredException, WrongNumberOfParameterException {
        //System.out.println("analyseMultiplicativeExpression!");
        analyseUnaryExpression();
        //System.out.println("Back from analyseUnaryExpression");
        while (true) {
            Token tk = nextToken();
            Token tmp = tk;
            //System.out.println(tk.getValue());
            if (tk.getType() != TokenType.TIMES_MARK && tk.getType() != TokenType.DIVISION_MARK) {
                unreadToken();
                break;
            }
            //unreadToken();
            analyseUnaryExpression();
            //System.out.println("Back from analyseUnaryExpression");

            //todo
            if (tmp.getType() == TokenType.TIMES_MARK) {
                if(isGlobal)
                    instruct.addStartInstruct(Operation.IMUL,0,0);
                else
                    instruct.addLocalInstruct(funcID,Operation.IMUL,0,0);
            }
            if (tmp.getType() == TokenType.DIVISION_MARK) {
                if(isGlobal)
                    instruct.addStartInstruct(Operation.IDIV,0,0);
                else
                    instruct.addLocalInstruct(funcID,Operation.IDIV,0,0);
            }
        }
    }

    //<unary-expression>::=[<unary-operator>]<primary-expression>
    private void analyseUnaryExpression() throws NoPreviousTokenException, SyntaxErrorException, MissingBracketException, NeedIdentifierException, NotDeclaredException, WrongNumberOfParameterException {
        //System.out.println("analyseUnaryExpression!");
        Token tk = nextToken();
        int PM=-1;
        //System.out.println(tk.getValue());
        if (tk.getType() != TokenType.PLUS_MARK && tk.getType() != TokenType.MINUS_MARK) {//如果现在没有符号
            unreadToken();
            analysePrimaryExpression(PM);
            //System.out.println("Back from analysePrimaryExpression");
        } else {//有符号的话

            if (tk.getType() == TokenType.PLUS_MARK) {
                //压进去加号
                PM=1;
            } else if (tk.getType() == TokenType.MINUS_MARK) {
                //压入减号
                PM=0;
            }
            analysePrimaryExpression(PM);
            //System.out.println("Back from analysePrimaryExpression");
        }
    }

    /*<primary-expression> ::=
    //     '('<expression>')'
    //    |<identifier>
    //    |<integer-literal>
    //    |<function-call>*/
    private void analysePrimaryExpression(int PM) throws NoPreviousTokenException, SyntaxErrorException, MissingBracketException, NeedIdentifierException, NotDeclaredException, WrongNumberOfParameterException {
        //System.out.println("analysePrimaryExpression!");
        Token tk = nextToken();
        //System.out.println(tk.getValue()+tk.getType());
        switch (tk.getType()) {
            case LEFT_BRACKET_MARK: {
                analyseExpression();
                //System.out.println("Back from analyseExpression");
                tk = nextToken();
                if (tk.getType() != TokenType.RIGHT_BRACKET_MARK) {
                    throw new MissingBracketException(currentPos);
                }
                break;
            }
            case IDENTIFIER: {
                if (isDeclaredFunc(tk) && isIntFunction(tk)) {
                    unreadToken();
                    //System.out.println("here");
                    analyseFunctionCall();
                    //  System.out.println("Back from analyseFunctionCall");
                    break;
                }
                if (isDeclared(tk)) {
                    //写指令
                    if(isGlobal){
                        instruct.addStartInstruct(Operation.LOADA,0,getGlobalIndex(tk.getValue()));
                        instruct.addStartInstruct(Operation.ILOAD,0,0);
                    }
                    else{
                        if(isGlobal(tk.getValue())){
                            instruct.addLocalInstruct(funcID,Operation.LOADA,1,getGlobalIndex(tk.getValue()));
                            instruct.addLocalInstruct(funcID,Operation.ILOAD,0,0);
                        }
                        else if(getLocalParaIndex(tk.getValue())!=-1){
                            instruct.addLocalInstruct(funcID,Operation.LOADA,0,getLocalParaIndex(tk.getValue()));
                            instruct.addLocalInstruct(funcID,Operation.ILOAD,0,0);
                        }
                        else if(isDeclared(tk)){
                            instruct.addLocalInstruct(funcID, Operation.LOADA,0,getLocalVarIndex(tk.getValue()));
                            instruct.addLocalInstruct(funcID,Operation.ILOAD,0,0);
                        }
                    }
                } else {
                    throw new NotDeclaredException(currentPos);
                }
                break;
            }
            case DECIMAL_INTEGER:
            case HEXADECIMAL_INTEGER: {
                //写指令
                //System.out.println("here");
                int n=Integer.parseInt(tk.getValue());
                if(isGlobal)
                    instruct.addStartInstruct(Operation.IPUSH,n,0);
                else
                    instruct.addLocalInstruct(funcID,Operation.IPUSH,n,0);
                break;
            }
            default:{
               //System.out.println("here");
                throw new SyntaxErrorException(currentPos);
            }
        }
        if(PM==0){
            if(isGlobal)
                instruct.addStartInstruct(Operation.INEG,0,0);
            else
                instruct.addLocalInstruct(funcID,Operation.INEG,0,0);
        }
    }

    //<loop-statement> ::='while' '(' <condition> ')' <statement>
    //todo loop instruction
    private void analyseLoopState() throws SyntaxErrorException, MissingBracketException, NoPreviousTokenException, MissingSemicolonException, NeedIdentifierException, NotDeclaredException, NoChangeConstException, WrongNumberOfParameterException, EndOfStatementException, NoNeedOfReturnException, NeedReturnStatementException {
        //System.out.println("analyseLoopState!");
        Token tk = nextToken();
        if (tk.getType() != TokenType.WHILE) {
            throw new SyntaxErrorException(currentPos);
        }
        tk = nextToken();
        if (tk.getType() != TokenType.LEFT_BRACKET_MARK) {
            throw new MissingBracketException(currentPos);
        }

        int size1=instruct.getPreCodeIndex(funcID);

        analyseCondition();
        tk = nextToken();
        if (tk.getType() != TokenType.RIGHT_BRACKET_MARK) {
            throw new MissingBracketException(currentPos);
        }

        int size2=instruct.getPreCodeIndex(funcID);

        analyseStatement();

        int size3=instruct.getPreCodeIndex(funcID);
        instruct.addLocalInstruct(funcID,Operation.JMP,size1,0);
        size3++;
        if(jmpType==1)
            instruct.addLocalInstruct(funcID,size2+1,Operation.JGE,size3,0);
        else if(jmpType==2)
            instruct.addLocalInstruct(funcID,size2+1,Operation.JLE,size3,0);
        else if(jmpType==3)
            instruct.addLocalInstruct(funcID,size2+1,Operation.JL,size3,0);
        else if(jmpType==4)
            instruct.addLocalInstruct(funcID,size2+1,Operation.JG,size3,0);
        else if(jmpType==5)
            instruct.addLocalInstruct(funcID,size2+1,Operation.JNE,size3,0);
        else if(jmpType==6)
            instruct.addLocalInstruct(funcID,size2+1,Operation.JE,size3,0);

    }

    //<condition-statement> ::='if' '(' <condition> ')' <statement> ['else' <statement>]
    //todo condition instruction
    private void analyseConditionState() throws SyntaxErrorException, MissingBracketException, NoPreviousTokenException, MissingSemicolonException, NeedIdentifierException, NotDeclaredException, NoChangeConstException, WrongNumberOfParameterException, EndOfStatementException, NoNeedOfReturnException, NeedReturnStatementException {
        //System.out.println("analyseConditionState!");
        Token tk = nextToken();
        if (tk.getType() != TokenType.IF) {
            throw new SyntaxErrorException(currentPos);
        }
        tk = nextToken();
        if (tk.getType() != TokenType.LEFT_BRACKET_MARK) {
            throw new MissingBracketException(currentPos);
        }
        analyseCondition();
        //todo
        //jmp
        tk = nextToken();
        if (tk.getType() != TokenType.RIGHT_BRACKET_MARK) {
            throw new MissingBracketException(currentPos);
        }
        int size1=instruct.getPreCodeIndex(funcID);///判断完条件以后的位置

        analyseStatement();

        int size2=instruct.getPreCodeIndex(funcID);//if后的代码结束以后的位置

        if(jmpType==1)
            instruct.addLocalInstruct(funcID,size1+1,Operation.JGE,size2,0);
        else if(jmpType==2)
            instruct.addLocalInstruct(funcID,size1+1,Operation.JLE,size2,0);
        else if(jmpType==3)
            instruct.addLocalInstruct(funcID,size1+1,Operation.JL,size2,0);
        else if(jmpType==4)
            instruct.addLocalInstruct(funcID,size1+1,Operation.JG,size2,0);
        else if(jmpType==5)
            instruct.addLocalInstruct(funcID,size1+1,Operation.JNE,size2,0);
        else if(jmpType==6)
            instruct.addLocalInstruct(funcID,size1+1,Operation.JE,size2,0);

        //['else']<statement>
        tk = nextToken();
        if (tk.getType() == TokenType.ELSE) {
            int size3=size2+1;
            analyseStatement();
            int size4=instruct.getPreCodeIndex(funcID);
            instruct.addLocalInstruct(funcID,size3+1,Operation.JMP,size4,0);
        } else {
            unreadToken();
        }
        jmpType=0;
    }

    //<condition>=<expression>[<relational-operator><expression>]
    private void analyseCondition() throws NoPreviousTokenException, SyntaxErrorException, MissingBracketException, NeedIdentifierException, NotDeclaredException, WrongNumberOfParameterException {
        //System.out.println("analyseCondition!");
        //<expression>
        analyseExpression();
        //System.out.println("Back from analyseExpression");
        //[<relational-operator><expression>]
        Token tk = nextToken();
        //
        switch (tk.getType()) {
            case GREATER_MARK: {
                tk = nextToken();
                if (tk.getType() == TokenType.EQUAL_MARK) {
                    jmpType=1;
                    analyseExpression();
                    if (true) ;//
                } else if (tk.getType() != TokenType.EQUAL_MARK) {
                    unreadToken();
                    jmpType=4;
                    analyseExpression();
                }
                break;
            }
            case SMALLER_MARK: {
                tk = nextToken();
                if (tk.getType() == TokenType.EQUAL_MARK) {
                    jmpType=2;
                    analyseExpression();
                } else if (tk.getType() != TokenType.EQUAL_MARK) {
                    jmpType=3;
                    unreadToken();
                    analyseExpression();
                }
                break;
            }
            case EXCLAMATION_MARK: {
                tk = nextToken();
                if (tk.getType() == TokenType.EQUAL_MARK) {
                    jmpType=5;
                    analyseExpression();
                    //todo
                    if (true) ;
                } else if (tk.getType() != TokenType.EQUAL_MARK) {
                    unreadToken();
                    throw new SyntaxErrorException(currentPos);
                }
                break;
            }
            case EQUAL_MARK: {
                tk = nextToken();
                if (tk.getType() == TokenType.EQUAL_MARK) {
                    jmpType=6;
                    analyseExpression();
                } else if (tk.getType() != TokenType.EQUAL_MARK) {
                    unreadToken();
                    throw new SyntaxErrorException(currentPos);
                }
                break;
            }
            default: {
                break;
            }
        }
    }


    //<parameter-declaration>=[<const-qualifier>]<type-specifier><identifier>
    //Unfinished
    //TODO
    void analyseParameterDeclaration() throws NeedTypeException, NeedIdentifierException {
        //System.out.println("analyseParameterDeclaration!");
        Token tk = nextToken();
        if (tk.getType() == TokenType.CONST) {
            tk = nextToken();
            if (tk.getType() != TokenType.INT) {
                throw new NeedTypeException(currentPos);
            }
            tk = nextToken();
            if (tk.getType() != TokenType.IDENTIFIER) {
                throw new NeedIdentifierException(currentPos);
            }
            //保存参数
            addConstant(new Variable(true,false,tk.getValue(),funcName));
            //函数参数声明 属于1层
        } else if (tk.getType() == TokenType.INT) {
            tk = nextToken();
            if (tk.getType() != TokenType.IDENTIFIER) {
                throw new NeedIdentifierException(currentPos);
            }
            //保存参数
            addVariable(new Variable(false,false,tk.getValue(),funcName));
            addUninitialized_vars(new Variable(false,false,tk.getValue(),funcName));
        }
        addLocalPara(tk.getValue());
    }

    //<variable-declaration>=[<const-qualifier>]<type-specifier><init-declarator-list>';'
    void analyseVariableDeclaration() throws MissingSemicolonException, NoPreviousTokenException, SyntaxErrorException, NeedTypeException, NeedIdentifierException, MissingBracketException, WrongNumberOfParameterException, NotDeclaredException, NeedValueForConstException, AlreadyDeclaredException {
        //System.out.println("analyseVariableDeclaration!");
        Token tk = nextToken();
        //System.out.println(tk.getValue());
        if (tk.getType() == TokenType.CONST) {//如果第一个是const
            tk = nextToken();//下一个
            if (tk.getType() != TokenType.INT) {//是不是int啊
                throw new NeedTypeException(currentPos);
            }
            //todo
            analyseInitDecList();//下一个读取
            //  System.out.println("Back from analyseInitDecList");

            tk = nextToken();
            //System.out.println(tk.getValue());
            if (tk.getType() != TokenType.SEMICOLON_MARK) {//分号有没有啊
                throw new MissingSemicolonException(currentPos);
            }
        }
        else {
            if (tk.getType() != TokenType.INT) {//既然不是const，如果不是int的话
                throw new NeedTypeException(currentPos);
            }
            //todo
            analyseInitDecList();
            //System.out.println("Back from analyseInitDecList");

            tk = nextToken();
            //System.out.println(tk.getValue());
            if (tk.getType() != TokenType.SEMICOLON_MARK) {
                throw new MissingSemicolonException(currentPos);
            }
        }

    }

    //<init-declarator-list> ::=<init-declarator>{','<init-declarator>}
    //TODO
    private void analyseInitDecList() throws NoPreviousTokenException, NeedIdentifierException, SyntaxErrorException, MissingBracketException, WrongNumberOfParameterException, NotDeclaredException, NeedValueForConstException, AlreadyDeclaredException {
        //System.out.println("analyseInitDecList!");
        analyseInitDeclarator();
        //System.out.println("Back from analyseInitDeclarator");
        while (true) {
            Token tk = nextToken();
            //System.out.println(tk.getValue());
            if (tk.getType() != TokenType.COMMA_MARK) {
                unreadToken();
                break;
            }
            analyseInitDeclarator();
            //System.out.println("Back from analyseInitDeclarator");
        }
    }

    //todo
    //<init-declarator>::=<identifier>[<initializer>]
    private void analyseInitDeclarator() throws NeedIdentifierException, NoPreviousTokenException, SyntaxErrorException, MissingBracketException, WrongNumberOfParameterException, NotDeclaredException, NeedValueForConstException, AlreadyDeclaredException {
        //System.out.println("analyseInitDeclarator!");
        Token tk = nextToken();
        // System.out.println(tk.getValue());
        if (tk.getType() != TokenType.IDENTIFIER) {
            throw new NeedIdentifierException(currentPos);
        }
        if(isLocalDeclared(tk)){
            throw new AlreadyDeclaredException(currentPos);
        }
        Token tmp = tk;
        if (this.isConst) {
            addConstant(new Variable(true,isGlobal,tmp.getValue(),funcName));
            if(!isGlobal)
                addLocalVar(tk.getValue());
        }
        addVariable(new Variable(false,isGlobal,tmp.getValue(),funcName));
        if(!isGlobal)
            addLocalVar(tk.getValue());
        tk = nextToken();
        //System.out.println(tk.getValue());
        if (tk.getType() == TokenType.EQUAL_MARK) {
            unreadToken();
            analyseInitializer();
            //System.out.println("Back from analyseInitializer");
        } else {
            if(isConst){
                throw new NeedValueForConstException(currentPos);
            }
            if(isGlobal){
                instruct.addStartInstruct(Operation.BIPUSH,0,0);
            }
            else{
                instruct.addLocalInstruct(funcID,Operation.BIPUSH,0,0);
            }
            unreadToken();
            addUninitialized_vars(new Variable(isConst,isGlobal,tmp.getValue(),funcName));
            if(!isGlobal)
                addLocalVar(tk.getValue());
        }
    }

    //todo
    //<initializer>::='='<expression>
    private void analyseInitializer() throws SyntaxErrorException, NoPreviousTokenException, MissingBracketException, NeedIdentifierException,WrongNumberOfParameterException, NotDeclaredException {
        //System.out.println("analyseInitializer!");
        Token tk = nextToken();
        if (tk.getType() != TokenType.EQUAL_MARK) {
            throw new SyntaxErrorException(currentPos);
        }
        analyseExpression();
        //System.out.println("Back from analyseExpression");
    }


    Token nextToken() {
        if (offset == tokens.size()) {
            return null;
        }
        currentPos = tokens.get(offset).getEndPos();
        return tokens.get(offset++);
    }

    void unreadToken() throws NoPreviousTokenException {
        if (offset == 0) {
            throw new NoPreviousTokenException(currentPos);
        }
        currentPos = tokens.get(offset - 1).getEndPos();
        offset--;
    }

    void addVariable(Variable var) {
        vars.add(var);
    }

    void addGlobal(String name){
        global.add(name);
    }

    void addConstant(Variable var){
        consts.add(var);
    }

    void addUninitialized_vars(Variable var) {
        uninitialized_vars.add(var);
    }

    void addFunction(Function f) {
        functions.add(f);
    }

    void delUninitialized_vars(Variable var) {
        uninitialized_vars.remove(var);
    }

    boolean isUninitializedVariable(Token tk) {
        int flag=0;
        for(Variable var:uninitialized_vars){
            if(var.getVariableName().equals(tk.getValue())&&(var.getFuncName().equals(funcName))){
                flag=1;
                return true;
            }
        }
        return false;
    }

    boolean isConstant(Token tk) {
        for(Variable var:consts){
            if(var.getVariableName().equals(tk.getValue())&&var.getFuncName().equals(funcName)){
                return true;
            }
        }
        return false;
    }

    boolean isDeclaredFunc(Token tk) {
        for(Function f:functions){
            if(f.getFuncName().equals(tk.getValue())){
                return true;
            }
        }
        return false;
    }

    boolean isIntFunction(Token tk) {
        for(Function f:functions){
            if(f.getFuncName().equals(tk.getValue()) &&!f.isVoidFunc())
                return true;

        }
        return false;
    }

    boolean isInitializedVariable(Token tk) {
        int flag=0;
        for(Variable var:vars){
            if(var.getVariableName().equals(tk.getValue())&&var.getFuncName().equals(funcName)&&!isUninitializedVariable(tk)){
                flag=1;
                return true;
            }
        }
        return false;
    }

    boolean isGlobal(String name){
        for(String str:global){
            if(str.equals(name))
                return true;
        }
        return false;
    }

    void addLocalPara(String name){
        localPara.add(name);
    }

    void addLocalVar(String name){
        localVar.add(name);
    }

    int getGlobalIndex(String name){
        for(int i=0;i<global.size();i++){
            if(global.get(i).equals(name))
                return i;
        }
        return -1;
    }

    int getFunctionIndex(String name){
        for(int i=0;i<functions.size();i++){
            if(functions.get(i).getFuncName().equals(name))
                return i;
        }
        return -1;
    }

    int getLocalVarIndex(String name){
        for (int i=0;i<localVar.size();i++){
            if(localVar.get(i).equals(name)){
                return i;
            }
        }
        return -1;
    }

    int getLocalParaIndex(String name){
        for (int i=0;i<localPara.size();i++){
            if(localPara.get(i).equals(name)){
                return i;
            }
        }
        return -1;
    }

    Function getFunction(Token tk){
        for(Function f:functions){
            if(f.getFuncName().equals(tk.getValue())){
                return f;
            }
        }
        return null;
    }

    boolean isDeclared(Token tk) {
        return isConstant(tk) || isInitializedVariable(tk) || isUninitializedVariable(tk)||isGlobal(tk.getValue());
    }

    boolean isLocalDeclared(Token tk){
        return (getLocalVarIndex(tk.getValue())!=-1&&getLocalParaIndex(tk.getValue())!=-1);
    }

    public void setTokens(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }
}

