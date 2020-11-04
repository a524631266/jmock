package com.zhangll.flink.parser;

import com.zhangll.flink.Father;
import com.zhangll.flink.model.FieldNode;
import com.zhangll.flink.model.FieldToken;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class NodeParserTest {
    NodeParser nodeParser;
    private Map<String, FieldToken> pojoTokenMap = null;
    @Before
    public void init(){
        nodeParser = new NodeParser();
        pojoTokenMap = null;
    }
    @Test
    public void get3TokenByNull() {

        FieldToken currentBasicToken = nodeParser.getCurrentBasicToken(null);
        // 2. 获取innercurrnetToken，一般是在Contain中（id，为列名）
        Map<String, FieldToken> innerPojoTokens = nodeParser.getInnerPojoTokens(null, pojoTokenMap);
        FieldToken innerBasicToken = nodeParser.getInnerBasicToken(null);
        // 这里的含义是 都返回空，表示没有实际数据
        assertTrue(currentBasicToken == null);
        assertTrue(innerPojoTokens != null);
        assertTrue(innerBasicToken == null);
    }

    @Test
    public void get3TokenByValue() throws NoSuchFieldException {
        Class<Father> fatherClass = Father.class;
        Field[] declaredFields = fatherClass.getDeclaredFields();
        Field money = fatherClass.getDeclaredField("money");
        FieldToken currentBasicToken = nodeParser.getCurrentBasicToken(money);
        Map<String, FieldToken> innerPojoTokens = nodeParser.getInnerPojoTokens(money, pojoTokenMap);
        FieldToken innerBasicToken = nodeParser.getInnerBasicToken(money);
        assertTrue(currentBasicToken != null);
        assertTrue(innerPojoTokens.isEmpty());
        assertTrue(innerBasicToken == null);



    }

    @Test
    public void testContainerForBasicType() throws NoSuchFieldException {
        Field sonsNameSet = Father.class.getDeclaredField("sonsNameSet");
        FieldToken currentBasicToken = nodeParser.getCurrentBasicToken(sonsNameSet);
        Map<String, FieldToken> innerPojoTokens = nodeParser.getInnerPojoTokens(sonsNameSet, pojoTokenMap);
        FieldToken innerBasicToken = nodeParser.getInnerBasicToken(sonsNameSet);

        assertTrue(currentBasicToken == null);
        assertTrue(innerPojoTokens.isEmpty());
        assertTrue(innerBasicToken != null);
        assertEquals(15, innerBasicToken.getMin());
        assertEquals(30, innerBasicToken.getMax());
    }

    @Test
    public void testContainerForPojoType() throws NoSuchFieldException {

        Field sonsNameSet = Father.class.getDeclaredField("sonslist");
        FieldToken currentBasicToken = nodeParser.getCurrentBasicToken(sonsNameSet);
        Map<String, FieldToken> innerPojoTokens = nodeParser.getInnerPojoTokens(sonsNameSet, pojoTokenMap);
        FieldToken innerBasicToken = nodeParser.getInnerBasicToken(sonsNameSet);

        assertTrue(currentBasicToken != null);
        assertTrue(!innerPojoTokens.isEmpty());
         // 一旦定义了ContainerTokenInfo 内部均不为空
        assertTrue(innerBasicToken!= null);
        FieldToken id = innerPojoTokens.get("id");
        assertEquals(1, id.getMin());
        assertEquals(10, id.getMax());

    }


    @Test
    public void testParseClass(){
        FieldNode node = nodeParser.initNodeTree(Father.class, null, pojoTokenMap);
        System.out.println(node);
    }
}