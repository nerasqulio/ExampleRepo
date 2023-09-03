/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shaderconfigurator;

import com.jogamp.common.nio.Buffers;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;

/**
 *
 * @author arda.eksi
 */
public class TypeConvertor {

    protected static MatrixCompound convertFromArrayToMatrix(Object obj, String type, Class annoType) {

        FloatBuffer matBuffer = null;
        DoubleBuffer matBufferD = null;
        Object tempObj = null;
        int first = 0;
        int second = 0;
        int finalLength = 0;
        boolean isSingleDimensional = true;

        int tempLength = 0;

        String tempType = type.contains("dmat") ? type.replaceFirst("dmat", "") : type.replaceFirst("mat", "");

        if (tempType.contains("x")) {

            second = Integer.parseInt("" + tempType.charAt(2));
            isSingleDimensional = false;
        }
        first = Integer.parseInt("" + tempType.charAt(0));
        finalLength = isSingleDimensional ? (first * first) : (first * second);
        tempObj = convert(obj, annoType);

        if (annoType.equals(float[].class) || annoType.equals(Float[].class)) {

            float[] tempArr = (float[]) tempObj;
            try {
                tempLength = (tempArr.length % finalLength == 0) ? (tempArr.length / finalLength) : -1;
                if (tempLength == -1) {
                    throw new IndexOutOfBoundsException("Array's length doesn't match the length of the Matrix uniform in GLSL!!");
                }
            } catch (IndexOutOfBoundsException ex) {

                System.err.println(ex.getMessage());
                System.exit(0);
            }
            matBuffer = Buffers.newDirectFloatBuffer(tempLength * finalLength);

        } else if (annoType.equals(double[].class) || annoType.equals(Double[].class)) {

            double[] tempArr = (double[]) tempObj;
            try {
                tempLength = (tempArr.length % finalLength == 0) ? (tempArr.length / finalLength) : -1;
                if (tempLength == -1) {
                    throw new IndexOutOfBoundsException("Array's length doesn't match the length of the Matrix uniform in GLSL!!");
                }
            } catch (IndexOutOfBoundsException ex) {
                System.err.println(ex.getMessage());
                System.exit(0);
            }
            matBufferD = Buffers.newDirectDoubleBuffer(tempLength * finalLength);
        }

        return (annoType.equals(Float[].class) || annoType.equals(float[].class)) ? new MatrixCompound(matBuffer.put((float[]) tempObj), tempLength, first, second) : new MatrixCompound(matBufferD.put((double[]) tempObj), tempLength, first, second);
    }

    protected static Object convert(Object obj, Class annoType) {

        try {
            if (annoType.equals(float[].class)) {
                return (float[]) obj;
            } else if (annoType.equals(double[].class)) {
                return (double[]) obj;
            } else if (annoType.equals(Double[].class)) {

                Double[] fl = (Double[]) obj;
                double[] tempArr = new double[fl.length];
                for (int i = 0; i < fl.length; i++) {
                    tempArr[i] = fl[i].doubleValue();
                }
                return tempArr;

            } else if (annoType.equals(Float[].class)) {

                Float[] fl = (Float[]) obj;
                float[] tempArr = new float[fl.length];
                for (int i = 0; i < fl.length; i++) {
                    tempArr[i] = fl[i].floatValue();
                }
                return tempArr;

            } else if (annoType.equals(Integer[].class)) {

                Integer[] fl = (Integer[]) obj;
                int[] tempArr = new int[fl.length];
                for (int i = 0; i < fl.length; i++) {
                    tempArr[i] = fl[i].intValue();
                }
                return tempArr;
            } else if (annoType.equals(int[].class)) {
                return (int[]) obj;
            } else if (annoType.equals(boolean[].class)) {

                boolean[] fl = (boolean[]) obj;
                int[] tempArr = new int[fl.length];
                for (int i = 0; i < fl.length; i++) {
                    tempArr[i] = fl[i] ? 1 : 0;
                }
                return tempArr;
            } else if (annoType.equals(Boolean[].class)) {

                Boolean[] fl = (Boolean[]) obj;
                int[] tempArr = new int[fl.length];
                for (int i = 0; i < fl.length; i++) {
                    tempArr[i] = fl[i].booleanValue() ? 1 : 0;
                }
                return tempArr;
            } else if (!obj.getClass().isArray()) {

                if (annoType.equals(Float.class)) {
                    return new float[]{((Float) (obj)).floatValue()};
                } else if (annoType.equals(float.class)) {
                    return new float[]{((float) (obj))};
                } else if (annoType.equals(Double.class)) {
                    return new double[]{((Double) (obj)).doubleValue()};
                } else if (annoType.equals(double.class)) {
                    return new double[]{((double) (obj))};
                } else if (annoType.equals(Integer.class)) {
                    return new int[]{((Integer) (obj)).intValue()};
                } else if (annoType.equals(int.class)) {
                    return new int[]{((int) (obj))};
                } else if (annoType.equals(Boolean.class)) {
                    return new int[]{((Boolean) obj).booleanValue() ? 1 : 0};
                } else if (annoType.equals(boolean.class)) {
                    return new int[]{((boolean) obj) ? 1 : 0};
                }
            } else {
                throw new ClassCastException("Check your 'Type' values in Annotations..Cast operation interrupted!! MATRICES SHOULD BE GIVEN AS SINGLE DIMENSIONAL ARRAYS!!");
            }

        } catch (ClassCastException ex) {
            System.err.println(ex.getMessage());
            System.exit(0);
        }
        return null;
    }

}
