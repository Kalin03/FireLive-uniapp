package com.kalin.utils;

import java.io.*;

public class ChatGLMResponse {
    public static String getHealthAnalyse(String sex,double height,double weight,int age) throws Exception {

        ChatGLM chatGLM = new ChatGLM();

        String str = "性别" + sex + ",身高" + height + "cm," + "体重" + weight + "kg," + age + "岁"
                + "这是我的信息，请根据我的信息准确计算出BMI值，并提供一段200左右字数的饮食和运动建议(不需要输出标题)，请严格按照例子的格式输出，" +
        "例子:根据你的BMI值，你的体重属于正常范围。这是一个好的开始！然而，除了BMI之外，还有其他因素需要考虑，比如饮食、运动等。由于你平时进" +
                "行中度运动，你的饮食应该保持均衡，包括充足的蛋白质、碳水化合物、蔬菜和水果。建议多摄入一些健康的脂肪，如坚果、鱼类等，并控制糖分和加工" +
                "食品的摄入。你也可以考虑增加一些有氧运动，比如慢跑、游泳或骑自行车,以增强心肺功能。此外，适当的力量训练也有助于增强" +
                "肌肉和骨骼。";

        // 创建一个 ByteArrayOutputStream 来捕获输出的内容
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream customPrintStream = new PrintStream(outputStream);

        // 将 System.out 设置为自定义的 PrintStream
        PrintStream oldPrintStream = System.out;
        System.setOut(customPrintStream);

        // 调用CharGLM
        chatGLM.getResult(str);

        // 将捕获的内容转换为字符串
        String printedString = outputStream.toString();

        // 恢复 System.out 的原始设置
        System.setOut(oldPrintStream);

        // 输出捕获的字符串
        //System.out.println("捕获到的输出内容为: " + printedString);

        return printedString;
    }

    public static void getTarget(String str) throws Exception {

        ChatGLM chatGLM = new ChatGLM();

        // 创建一个 ByteArrayOutputStream 来捕获输出的内容
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream customPrintStream = new PrintStream(outputStream);

        // 将 System.out 设置为自定义的 PrintStream
        PrintStream oldPrintStream = System.out;
        System.setOut(customPrintStream);

        // 调用CharGLM
        chatGLM.getResult(str);

        // 将捕获的内容转换为字符串
        String printedString = outputStream.toString();

        // 恢复 System.out 的原始设置
        System.setOut(oldPrintStream);

        // 输出捕获的字符串
//        System.out.println("捕获到的输出内容为: " + printedString);
    }
}
