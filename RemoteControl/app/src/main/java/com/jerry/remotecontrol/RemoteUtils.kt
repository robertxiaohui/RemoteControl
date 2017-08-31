package com.jerry.remotecontrol

import android.util.Log

/**
 * Created by lmh on 2017/8/29.
 */
class RemoteUtils {
    companion object {

        /**
         * 16进制转2进制
         */
        fun hexString2binaryString(hexString: String?): String {
            if (hexString == null)
                return ""

            var newStr: String = hexString
            if (hexString!!.length == 1){
                newStr = "0"+hexString
            }

            var bString:String = ""
            var tmp: String
            for (i in 0..newStr.length - 1) {
                tmp = "0000" + Integer.toBinaryString(Integer.parseInt(newStr
                        .substring(i, i + 1), 16))
                bString += tmp.substring(tmp.length - 4)
            }
            return bString
        }

        /**
         * 创建一个指令,规则:
         * 起始码:4000us(高),4000us(低)
         * 逻辑1:500us(高),1900us(低)
         * 逻辑0:500us(高),900us(低)
         * 固定码:1111,十六进制F
         * 结束码:500us(高)
         * 拼接顺序:起始码+固定码+8位指令码+固定码反码+8位指令码反码+结束码
         */
        fun creatOrder(hexString: String?): IntArray {
            Log.d("TAG","hexString = "+hexString)
            var orderList:IntArray = IntArray(51)
            //起始码
            orderList.set(0, 4000)
            orderList.set(1, 4000)
            //结束码
            orderList.set(50, 500)

            //固定码
            var commenCode = "1111"
            //指令码
            var binStr = hexString2binaryString(hexString)

            //添加固定码
            orderList = addArrToBigArr(2, strToArr(commenCode, false), orderList)
            //添加指令码
            orderList = addArrToBigArr(10, strToArr(binStr, false), orderList)
            //添加固定码反码
            orderList = addArrToBigArr(26, strToArr(commenCode, true), orderList)
            //添加指令码反码
            orderList = addArrToBigArr(34, strToArr(binStr, true), orderList)

//            print(orderList)
          //  Log.d("TAG",orderList.toString())
            var str:String = ""
            for (index in orderList.indices){
              str = str+ orderList[index]+","
            }
            Log.d("TAG",str)
            return orderList
        }

        /**
         * 将二进制的字符串转换成对应的时间数组
         * @param binStr 二进制字符串
         * @param isShift 是否转换成反码
         * @return 返回对应时间的数组
         */
        fun strToArr(binStr: String, isShift: Boolean): IntArray {

            var arr = binStr!!.toCharArray()
            var orderList: IntArray = IntArray(arr.size*2)
            for (index in arr.indices) {
                if (isShift) {
                    if (arr[index] == '0') {
                        orderList.set(index * 2, 500)
                        orderList.set(index * 2 + 1, 1900)
                    } else {
                        orderList.set(index * 2, 500)
                        orderList.set(index * 2 + 1, 900)
                    }
                } else {
                    if (arr[index] == '1') {
                        orderList.set(index * 2, 500)
                        orderList.set(index * 2 + 1, 1900)
                    } else {
                        orderList.set(index * 2, 500)
                        orderList.set(index * 2 + 1, 900)
                    }
                }


            }
            return orderList
        }

        /**
         * 将小数租添加到大数组里面
         * @param startIndex 添加位置的起始坐标
         * @param smallArr 小数组
         * @param bigArr 大数组
         * @return 返回大数组
         */
        fun addArrToBigArr(startIndex: Int, smallArr: IntArray, bigArr:IntArray): IntArray {
            for (index in smallArr.indices) {
                bigArr.set(startIndex + index, smallArr[index])
            }
            return bigArr
        }
    }
}