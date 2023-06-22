package com.larissa.android.quiz;
import java.util.Stack;

public class Calculator {
    int zhengfu_flag =1;
    public Calculator() {}
    public String compute(String str) {
        Stack<Character> stack_symbols;
        Stack<Double> stack_numbers;
        Stack<Character> stack_bracket_check;
        stack_bracket_check = new Stack<Character>();
        stack_numbers = new Stack<Double>();
        stack_symbols = new Stack<Character>();
        int i,t,special_result;
        char s;
        boolean result;
        double num=0, a = 0.1;


        for(i = 0;i < str.length();i++) {           //先对传入的算式字符串进行检查，若扩号出现不对应的情况，则返回错误，这样可以减少运算的工作量
            if(str.charAt(i) == '(') stack_bracket_check.push(str.charAt(i));
            else if(str.charAt(i) == ')') {
                if(!stack_bracket_check.empty()) stack_bracket_check.pop();
                else return "出错";
            }
        }
        if(!stack_bracket_check.empty()) return "出错";
                                                 //算式格式正确后进行算式的运算
        for(i = 0;i < str.length();i++) {
            s = str.charAt(i);
            if(s == 'e'||s == 'π'|| s>='0'&&s<='9'){
                t = i;    //这里的t用于记录i此时对应的字符位置
                num = 0;
                if(s == 'e') num = Math.E;
                else if(s == 'π') num = Math.PI;
                else {
                    while(t<str.length()&&str.charAt(t)>='0'&&str.charAt(t)<='9') {   //注意，这里t<str.length()必须要写在前面，否则str.charAt(t)>='0'先计算的话会因为t超出数组索引而报错
                        num = num*10+(str.charAt(t)-'0');   //由于str.charAt(t)取出来是字符，直接运算算的是ascii值，故要减去‘0’
                        t++;
                    }
                    if(t<str.length()&&str.charAt(t) == '.'){
                        t++;
                        while(t<str.length()&&str.charAt(t)>='0'&&str.charAt(t)<='9') {
                            num += (str.charAt(t) -'0')*a;
                            t++;
                            a *= 0.1;
                        }
                    }
                    i = t-1;
                }
                stack_numbers.push(zhengfu_flag * num);
                zhengfu_flag = 1;    //上面的步骤是判断是否为数，是的话把数取出来，然后压入堆栈之中！
            }
            else if(s == '+'||s == '-'||s == '*'||s == '/'){
                if(s == '-'&&(i == 0||str.charAt(i-1)=='*'||str.charAt(i-1)=='/'||str.charAt(i-1)=='('))
                    zhengfu_flag = -1;  //这一步是判断‘-’是负号还是减号，通过zhengfu_flag标志，在上面数的压栈中选择压入正数或者负数
                else {
                    while(!stack_symbols.empty()&&stack_symbols.peek()!='('
                            &&get_symbol_priority(s)<=get_symbol_priority(stack_symbols.peek())) {
                        result = compute_concrete(stack_numbers,stack_symbols);      //当判断到符号为加减乘除时，若符号栈不为空，栈顶不为左括号
                        if(!result) return "出错";                                    //这时根据符号的优先级进行具体数值的运算，得到的计算结果压入栈中，从而将整个算式一步步化简
                    }
                    stack_symbols.push(s);
                }
            }
            else if(s == 's'||s == 'c'||s == 't'||s == 'l'||s == '^'||s == '√'||s == '!') {   //判断到是特殊运算，调用特殊运算的函数
                if(s == '^' || s == '!') {
                    if(i==0) return "出错"; //对于阶乘和次方的输入进行限制
                }

                special_result = special_compute(stack_numbers,stack_symbols,str.substring(i));
                if(special_result == -1) return "出错";
                i += special_result;

            }
            else if(s == '(') {
                stack_symbols.push(s);
            }
            else if(s == ')') {
                while(stack_symbols.peek() != '(') {    //这一步是将括号里的算式先算出结果然后压入堆栈
                    if(!compute_concrete(stack_numbers,stack_symbols)) return "出错";
                }
                stack_symbols.pop();  //计算结束后弹出栈顶的'('
            }
        }
        while(!stack_symbols.empty()) {     //若发现符号栈仍为空就做具体的运算
            if(!compute_concrete(stack_numbers,stack_symbols)) return "出错";
        }

        if(stack_numbers.empty()) return "出错";
        return Double.toString(stack_numbers.peek());   //将结果以字符形式输出
    }

    /* 该函数的作用是对栈中的数进行具体数值运算，将得到的结果压入栈中*/
    private boolean compute_concrete(Stack<Double> stack_numbers,Stack<Character> stack_symbols) {
        if(stack_numbers.empty()) return false;
        double a = stack_numbers.peek(),sum=0;
        stack_numbers.pop();
        if(stack_numbers.empty()) return false;  //这里要保证数字栈中的数字有两个
        double b = stack_numbers.peek();
        stack_numbers.pop();
        char s = stack_symbols.peek();
        stack_symbols.pop();
        switch(s) {
            case '+':sum = b+a;break;  //对数值进行运算
            case '-':sum = b-a;break;
            case '*':sum = b*a;break;
            case '/':sum = b/a;break;
        }
        stack_numbers.push(sum);
        return true;
    }

    private int get_symbol_priority(char s) {   //这个函数用来判断符号优先级
        int a = 0;
        switch(s)
        {
            case '+':
            case '-':a = 1;break;
            case '*':
            case '/':a = 2;break;
        }
        return a;
    }

    private int get_special_compute_lastlocation(String str,int start_location) {  //这个函数用来获取特殊运算，如sin，cos中算式的最后一位
        int i;
        Stack<Character> stack;
        stack=new Stack<Character>();
        stack.push('(');
        for(i = start_location+1;i<str.length()&&!stack.empty();i++) {
            if(str.charAt(i) == '(') stack.push('(');
            else if(str.charAt(i) == ')') stack.pop();
        }
        return i-1;
    }

    private int special_compute(Stack<Double> stack_numbers,Stack<Character> stack_symbols,String s) {
        int return_last_location=0;
        String result;
        Double p;
        double num,sum=1.0;
        switch(s.charAt(0)){         //判断第一位具体是哪一种运算
            case 's': {
                return_last_location = get_special_compute_lastlocation(s,3); //获取到sin算式里面最后一位数的位置
                result = compute(s.substring(4,return_last_location));    //通过递归调用copmute函数计算出sin算式里面的值，其他特殊运算也是类似的处理方式
                if(result == "出错") return -1;
                stack_numbers.push(Math.sin(Math.toRadians(Double.parseDouble(result)))); //Math.toRadians作用是将数据转换为弧度制
                break;
            }
            case 'c': {
                return_last_location = get_special_compute_lastlocation(s,3);
                result = compute(s.substring(4,return_last_location));
                if(result == "出错") return -1;
                stack_numbers.push(Math.cos(Math.toRadians(Double.parseDouble(result))));
                break;
            }
            case 't': {
                return_last_location = get_special_compute_lastlocation(s,3);
                result = compute(s.substring(4,return_last_location));
                if(result == "出错") return -1;
                p = Math.tan(Math.toRadians(Double.parseDouble(result)));  //这里对tan数值的范围进行判断，因为tan可以达到无穷
                if(Math.abs(p)>1e10) return -1;
                stack_numbers.push(p);
                break;
            }
            case '!': {
                num = stack_numbers.peek();
                stack_numbers.pop();
                if(num<0) return -1;
                if(num == 0.0 || num == 1.0) sum = 1.0;
                else {
                    while(num>1.0){
                        sum*=num;
                        num--;
                    }
                }
                stack_numbers.push(sum);
                break;
            }
            case '√':{
                return_last_location = get_special_compute_lastlocation(s,1);
                result = compute(s.substring(2,return_last_location));
                if(result == "出错") return -1;
                stack_numbers.push(Math.sqrt(Double.parseDouble(result)));
                break;
            }
            case 'l': {
                return_last_location = get_special_compute_lastlocation(s,2);
                result = compute(s.substring(3,return_last_location));
                if(result == "出错") return -1;
                stack_numbers.push(Math.log(Double.parseDouble(result)));              //log函数即为ln
                break;
            }
            case '^':{
                return_last_location = get_special_compute_lastlocation(s,1);
                result = compute(s.substring(2,return_last_location));
                if(result == "出错") return -1;
                num = stack_numbers.peek();
                stack_numbers.pop();
                stack_numbers.push(Math.pow(num,Double.parseDouble(result)));   //这一步可以处理四种情况，即x的平方，立方，y次方，以及x的导数（x^(-1)）
                break;
            }
        }
        return return_last_location;
    }
}
