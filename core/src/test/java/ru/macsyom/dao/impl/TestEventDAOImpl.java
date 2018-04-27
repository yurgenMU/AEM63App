package ru.macsyom.dao.impl;


import org.apache.sling.jcr.api.SlingRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import javax.jcr.Node;
import javax.jcr.Session;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;


public class TestEventDAOImpl {



    private EventDAOImpl eventDAO;

    @Before
    public void setUp() throws Exception {
        eventDAO = new EventDAOImpl();
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void generatedPostfixEquality() {
        String postFix1 = eventDAO.generatePostfix();
        String postFix2 = "";
        for (int i = 0; i < 1001; i++) {
            postFix2 = eventDAO.generatePostfix();
            Assert.assertNotEquals(postFix1, postFix2);
        }

    }

    @Test
    public void generatedPostfixAccessibleChars(){
        String postfix = eventDAO.generatePostfix();
        boolean isLowerCase = postfix.equals(postfix.toLowerCase());
        Assert.assertTrue(isLowerCase);
    }


    @Test
    public void generatedPostfixNumbers(){
        String postfix = eventDAO.generatePostfix();
        Assert.assertFalse(postfix.matches(".*\\d+.*"));
    }

    @Test
    public void generatedPostfixLength(){
        String postFix = eventDAO.generatePostfix();
        Assert.assertTrue(postFix.length() == 10);
    }

    @Test
    public void nullRequestParam(){
        String[] array = {"param1", "param2", null};
        Assert.assertTrue(eventDAO.checkIfNullParams(array));
    }



}