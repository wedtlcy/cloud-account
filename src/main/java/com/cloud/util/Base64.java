package com.cloud.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

  
  
  
public class Base64 {  
    private static final char[] legalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();  
    public static void main(String args) {
    	
    }
    /** 
     * data[]进行编码 
     * @param data 
     * @return 
     */  
        public static String encode(byte[] data) {  
            int start = 0;  
            int len = data.length;  
            StringBuffer buf = new StringBuffer(data.length * 3 / 2);  
  
            int end = len - 3;  
            int i = start;  
            int n = 0;  
  
            while (i <= end) {  
                int d = ((((int) data[i]) & 0x0ff) << 16)  
                        | ((((int) data[i + 1]) & 0x0ff) << 8)  
                        | (((int) data[i + 2]) & 0x0ff);  
  
                buf.append(legalChars[(d >> 18) & 63]);  
                buf.append(legalChars[(d >> 12) & 63]);  
                buf.append(legalChars[(d >> 6) & 63]);  
                buf.append(legalChars[d & 63]);  
  
                i += 3;  
  
//                if (n++ >= 14) {  
//                    n = 0;  
//                    buf.append(" ");  
//                }  
            }  
  
            if (i == start + len - 2) {  
                int d = ((((int) data[i]) & 0x0ff) << 16)  
                        | ((((int) data[i + 1]) & 255) << 8);  
  
                buf.append(legalChars[(d >> 18) & 63]);  
                buf.append(legalChars[(d >> 12) & 63]);  
                buf.append(legalChars[(d >> 6) & 63]);  
                buf.append("=");  
            } else if (i == start + len - 1) {  
                int d = (((int) data[i]) & 0x0ff) << 16;  
  
                buf.append(legalChars[(d >> 18) & 63]);  
                buf.append(legalChars[(d >> 12) & 63]);  
                buf.append("==");  
            }  
  
            return buf.toString();  
        }  
  
        private static int decode(char c) throws Exception {  
            if (c >= 'A' && c <= 'Z')  
                return ((int) c) - 65;  
            else if (c >= 'a' && c <= 'z')  
                return ((int) c) - 97 + 26;  
            else if (c >= '0' && c <= '9')  
                return ((int) c) - 48 + 26 + 26;  
            else  
                switch (c) {  
                case '+':  
                    return 62;  
                case '/':  
                    return 63;  
                case '=':  
                    return 0;  
                default:  
                	throw new Exception("ResultCode.DECODEBARWRONG"); 
                }  
        }  
  
        /** 
         * Decodes the given Base64 encoded String to a new byte array. The byte 
         * array holding the decoded data is returned. 
         * @throws Exception 
         */  
  
        public static byte[] decode(String s) throws Exception {  
            ByteArrayOutputStream bos = new ByteArrayOutputStream();  
            byte[] decodedBytes = null;
            try {  
//                decode(s, bos);  
            	if(null!=s&&!"".equals(s)&&s.length()>0){
	            	 int i = 0;  
	                 int len = s.length(); 
	     	            while (true) {  
	     	                while (i < len && s.charAt(i) <= ' ')  
	     	                    i++;  
	     	                if (i == len)  
	     	                    break;  
	     	                int tri = (decode(s.charAt(i)) << 18)  
	     	                        + (decode(s.charAt(i + 1)) << 12)  
	     	                        + (decode(s.charAt(i + 2)) << 6)  
	     	                        + (decode(s.charAt(i + 3)));  
	     	                bos.write((tri >> 16) & 255);
	     	                if (s.charAt(i + 2) == '=')  
	     	                    break;  
	     	               bos.write((tri >> 8) & 255);  
	     	                if (s.charAt(i + 3) == '=')  
	     	                    break;  
	     	               bos.write(tri & 255);  
	     	                i += 4;  
	     	            }  
	            	decodedBytes = bos.toByteArray();  
            	}
            } catch (NullPointerException e) {  
            	e.printStackTrace();
                throw new Exception("ResultCode.DECODEBARWRONG"); 
            } catch (IOException e) {  
                throw new Exception("ResultCode.DECODEBARWRONG"); 
            }finally{
            	if(null!=bos){
            		 bos.close(); 
            	}
            }  
            return decodedBytes;  
        }  
  
        private static void decode(String s, OutputStream os) throws Exception {  
            int i = 0;  
            int len = s.length();  
            try {
	            while (true) {  
	                while (i < len && s.charAt(i) <= ' ')  
	                    i++;  
	                if (i == len)  
	                    break;  
	                int tri = (decode(s.charAt(i)) << 18)  
	                        + (decode(s.charAt(i + 1)) << 12)  
	                        + (decode(s.charAt(i + 2)) << 6)  
	                        + (decode(s.charAt(i + 3)));  
	                
						os.write((tri >> 16) & 255);
				
	                if (s.charAt(i + 2) == '=')  
	                    break;  
	                os.write((tri >> 8) & 255);  
	                if (s.charAt(i + 3) == '=')  
	                    break;  
	                os.write(tri & 255);  
	                i += 4;  
	            }  
            } catch (IOException e) {
				// TODO Auto-generated catch block
                throw new Exception("ResultCode.DECODEBARWRONG"); 
			}  
        }
		public static void main(String args[]){
			String info = "PGRpdj48dGFibGUgY2VsbHNwYWNpbmc9IjAiIGNlbGxwYWRkaW5nPSIwIiB2c3BhY2U9IjAiIGhzcGFjZT0iMCI+PHRib2R5Pjx0cj48dGQgc3R5bGU9IkJPUkRFUi1CT1RUT006ICMwMDAwMDA7IEJPUkRFUi1MRUZUOiAjMDAwMDAwOyBQQURESU5HLUJPVFRPTTogMHB4OyBCQUNLR1JPVU5ELUNPTE9SOiB0cmFuc3BhcmVudDsgUEFERElORy1MRUZUOiAzcHg7IFBBRERJTkctUklHSFQ6IDNweDsgQk9SREVSLVRPUDogIzAwMDAwMDsgQk9SREVSLVJJR0hUOiAjMDAwMDAwOyBQQURESU5HLVRPUDogMHB4IiB2YWxpZ249InRvcCI+PHAgc3R5bGU9IlRFWFQtQUxJR046IGNlbnRlcjsgTElORS1IRUlHSFQ6IDI3cHgiPjxzcGFuIHN0eWxlPSJGT05ULUZBTUlMWTog5a6L5L2TOyBDT0xPUjogIzExMTExMTsgRk9OVC1TSVpFOiAxNnB4Ij48c3BhbiBzdHlsZT0iQkFDS0dST1VORDogd2hpdGUiPjwvc3Bhbj48L3NwYW4+Jm5ic3A7PC9wPjxwIHN0eWxlPSJMSU5FLUhFSUdIVDogMzJweDsgVEVYVC1JTkRFTlQ6IDEyNnB4OyBCQUNLR1JPVU5EOiB3aGl0ZSI+PHN0cm9uZz48c3BhbiBzdHlsZT0iRk9OVC1GQU1JTFk6IOaWueato+Wwj+agh+Wui+eugOS9kzsgTEVUVEVSLVNQQUNJTkc6IDFweDsgQ09MT1I6IGJsYWNrOyBGT05ULVNJWkU6IDI5cHhmb250LWZhbWlseTpUaW1lcyBOZXcgUm9tYW4iPuaLjeWNluagh+eahOeJqeiwg+afpeaDheWGteihqDwvc3Bhbj48L3N0cm9uZz48L3A+PHAgc3R5bGU9IlRFWFQtQUxJR046IGNlbnRlcjsgTElORS1IRUlHSFQ6IDMycHg7IEJBQ0tHUk9VTkQ6IHdoaXRlIj48c3Ryb25nPjxzcGFuIHN0eWxlPSJGT05ULUZBTUlMWTog5a6L5L2TOyBDT0xPUjogYmxhY2s7IEZPTlQtU0laRTogMjBweCI+77yIPHNwYW4+MjAxODwvc3Bhbj7vvInmlrA8c3Bhbj4wMTAyPC9zcGFuPuaJp+aBojxzcGFuPjQ8L3NwYW4+5Y+3PC9zcGFuPjwvc3Ryb25nPjxzdHJvbmc+PC9zdHJvbmc+PC9wPjx0YWJsZSBzdHlsZT0iUEFERElORy1CT1RUT006IDBweDsgUEFERElORy1MRUZUOiAwcHg7IFBBRERJTkctUklHSFQ6IDBweDsgQk9SREVSLUNPTExBUFNFOiBjb2xsYXBzZTsgUEFERElORy1UT1A6IDBweCIgYm9yZGVyPSIwIiBjZWxsc3BhY2luZz0iMCIgY2VsbHBhZGRpbmc9IjAiPjx0Ym9keT48dHIgc3R5bGU9IkhFSUdIVDogNjJweCI+PHRkIHN0eWxlPSJCT1JERVItQk9UVE9NOiBibGFjayAxcHggc29saWQ7IEJPUkRFUi1MRUZUOiBibGFjayAxcHggc29saWQ7IFBBRERJTkctQk9UVE9NOiAwcHg7IEJBQ0tHUk9VTkQtQ09MT1I6IHRyYW5zcGFyZW50OyBQQURESU5HLUxFRlQ6IDZweDsgV0lEVEg6IDExNnB4OyBQQURESU5HLVJJR0hUOiA2cHg7IEhFSUdIVDogNjJweDsgQk9SREVSLVRPUDogYmxhY2sgMXB4IHNvbGlkOyBCT1JERVItUklHSFQ6IGJsYWNrIDFweCBzb2xpZDsgUEFERElORy1UT1A6IDBweCIgd2lkdGg9IjExNiI+PHAgc3R5bGU9IlRFWFQtQUxJR046IGNlbnRlcjsgTElORS1IRUlHSFQ6IDI3cHgiPjxzcGFuIHN0eWxlPSJGT05ULUZBTUlMWTog5a6L5L2TOyBGT05ULVNJWkU6IDE2cHhjb2xvcjojMDAwMDAwIj7mi43lk4HlkI3np7A8L3NwYW4+PC9wPjwvdGQ+PHRkIHN0eWxlPSJCT1JERVItQk9UVE9NOiBibGFjayAxcHggc29saWQ7IEJPUkRFUi1MRUZUOiAjMDAwMDAwOyBQQURESU5HLUJPVFRPTTogMHB4OyBCQUNLR1JPVU5ELUNPTE9SOiB0cmFuc3BhcmVudDsgUEFERElORy1MRUZUOiA2cHg7IFdJRFRIOiA0NDJweDsgUEFERElORy1SSUdIVDogNnB4OyBIRUlHSFQ6IDYycHg7IEJPUkRFUi1UT1A6IGJsYWNrIDFweCBzb2xpZDsgQk9SREVSLVJJR0hUOiBibGFjayAxcHggc29saWQ7IFBBRERJTkctVE9QOiAwcHgiIHdpZHRoPSI0NDIiIGNvbHNwYW49IjIiPjxwIHN0eWxlPSJURVhULUFMSUdOOiBsZWZ0OyBNQVJHSU4tVE9QOiBhdXRvOyBNQVJHSU4tQk9UVE9NOiBhdXRvIj48c3BhbiBzdHlsZT0iRk9OVC1GQU1JTFk6IOWui+S9kzsgRk9OVC1TSVpFOiAxNnB4Y29sb3I6IzAwMDAwMCI+5L2N5LqO5LmM6bKB5pyo6b2Q5biC5aSp5bGx5Yy657+g5rOJ6LevPHNwYW4+NjE0PC9zcGFuPuWPt+mygeePrea4qemmqOiKseWbreWwj+WMujxzcGFuPjI8L3NwYW4+5qCLPHNwYW4+Njwvc3Bhbj7lsYI8c3Bhbj4yPC9zcGFuPuWNleWFgzxzcGFuPjYwMTwvc3Bhbj7lrqQ8L3NwYW4+PC9wPjwvdGQ+PC90cj48dHIgc3R5bGU9IkhFSUdIVDogNjBweCI+PHRkIHN0eWxlPSJCT1JERVItQk9UVE9NOiBibGFjayAxcHggc29saWQ7IEJPUkRFUi1MRUZUOiBibGFjayAxcHggc29saWQ7IFBBRERJTkctQk9UVE9NOiAwcHg7IEJBQ0tHUk9VTkQtQ09MT1I6IHRyYW5zcGFyZW50OyBQQURESU5HLUxFRlQ6IDZweDsgV0lEVEg6IDExNnB4OyBQQURESU5HLVJJR0hUOiA2cHg7IEhFSUdIVDogNjBweDsgQk9SREVSLVRPUDogIzAwMDAwMDsgQk9SREVSLVJJR0hUOiBibGFjayAxcHggc29saWQ7IFBBRERJTkctVE9QOiAwcHgiIHdpZHRoPSIxMTYiPjxwIHN0eWxlPSJURVhULUFMSUdOOiBjZW50ZXI7IExJTkUtSEVJR0hUOiAyN3B4Ij48c3BhbiBzdHlsZT0iRk9OVC1GQU1JTFk6IOWui+S9kzsgRk9OVC1TSVpFOiAxNnB4Y29sb3I6IzAwMDAwMCI+5p2D6K+B5oOF5Ya1PC9zcGFuPjwvcD48L3RkPjx0ZCBzdHlsZT0iQk9SREVSLUJPVFRPTTogYmxhY2sgMXB4IHNvbGlkOyBCT1JERVItTEVGVDogIzAwMDAwMDsgUEFERElORy1CT1RUT006IDBweDsgQkFDS0dST1VORC1DT0xPUjogdHJhbnNwYXJlbnQ7IFBBRERJTkctTEVGVDogNnB4OyBXSURUSDogNDQycHg7IFBBRERJTkctUklHSFQ6IDZweDsgSEVJR0hUOiA2MHB4OyBCT1JERVItVE9QOiAjMDAwMDAwOyBCT1JERVItUklHSFQ6IGJsYWNrIDFweCBzb2xpZDsgUEFERElORy1UT1A6IDBweCIgd2lkdGg9IjQ0MiIgY29sc3Bhbj0iMiI+PHAgc3R5bGU9IlRFWFQtSU5ERU5UOiAwcHg7IE1BUkdJTjogYXV0byAwcHggYXV0byAxNnB4Ij48c3BhbiBzdHlsZT0iRk9OVC1GQU1JTFk6IOWui+S9kzsgRk9OVC1TSVpFOiAxNnB4Ij48c3BhbiBzdHlsZT0idW5kZWZpbmVkY29sb3I6IzAwMDAwMCI+MS48c3BhbiBzdHlsZT0iRk9OVDogOXB4ICYjMzk7VGltZXMgTmV3IFJvbWFuJiMzOTsiPiZuYnNwOyZuYnNwOyZuYnNwOyZuYnNwOyZuYnNwOyZuYnNwOyZuYnNwOyZuYnNwOyZuYnNwOyZuYnNwOyZuYnNwOyA8L3NwYW4+PC9zcGFuPjwvc3Bhbj48c3BhbiBzdHlsZT0iRk9OVC1GQU1JTFk6IOWui+S9kzsgRk9OVC1TSVpFOiAxNnB4Ij48c3BhbiBzdHlsZT0iY29sb3I6IzAwMDAwMCI+5omn6KGM6KOB5a6a5Lmm77ya77yIPHNwYW4+MjAxODwvc3Bhbj7vvInmlrA8c3Bhbj4wMTAyPC9zcGFuPuaJp+aBojxzcGFuPjQ8L3NwYW4+5Y+377ybPC9zcGFuPjxzcGFuPjxici8+PHNwYW4gc3R5bGU9ImNvbG9yOiMwMDAwMDAiPjIuPHNwYW4+Jm5ic3A7Jm5ic3A7IDwvc3Bhbj48L3NwYW4+PC9zcGFuPjxzcGFuIHN0eWxlPSJjb2xvcjojMDAwMDAwIj7miL/kuqfor4Hlj7fvvJrkuYzmiL/mnYPor4Hlj7flpKnlsbHljLrlrZfnrKw8c3Bhbj4yMDEyNDA4MTE3PC9zcGFuPuWPtzwvc3Bhbj48L3NwYW4+PC9wPjxwIHN0eWxlPSJNQVJHSU4tVE9QOiBhdXRvOyBURVhULUlOREVOVDogMTZweDsgTUFSR0lOLUJPVFRPTTogYXV0byI+PHNwYW4gc3R5bGU9ImNvbG9yOiMwMDAwMDAiPjxzcGFuIHN0eWxlPSJGT05ULUZBTUlMWTog5a6L5L2TOyBGT05ULVNJWkU6IDE2cHgiPjxzcGFuPjPjgIEgPC9zcGFuPjwvc3Bhbj48c3BhbiBzdHlsZT0iRk9OVC1GQU1JTFk6IOWui+S9kzsgRk9OVC1TSVpFOiAxNnB4Ij7lnJ/lnLDor4Hlj7fvvJrkuYzluILlm73nlKjvvIg8c3Bhbj4yMDEyPC9zcGFuPu+8ieesrDxzcGFuPjAwMDI4NDE5PC9zcGFuPuWPtzwvc3Bhbj48L3NwYW4+PC9wPjwvdGQ+PC90cj48dHIgc3R5bGU9IkhFSUdIVDogNDJweCI+PHRkIHN0eWxlPSJCT1JERVItQk9UVE9NOiBibGFjayAxcHggc29saWQ7IEJPUkRFUi1MRUZUOiBibGFjayAxcHggc29saWQ7IFBBRERJTkctQk9UVE9NOiAwcHg7IEJBQ0tHUk9VTkQtQ09MT1I6IHRyYW5zcGFyZW50OyBQQURESU5HLUxFRlQ6IDZweDsgV0lEVEg6IDExNnB4OyBQQURESU5HLVJJR0hUOiA2cHg7IEhFSUdIVDogNDJweDsgQk9SREVSLVRPUDogIzAwMDAwMDsgQk9SREVSLVJJR0hUOiBibGFjayAxcHggc29saWQ7IFBBRERJTkctVE9QOiAwcHgiIHdpZHRoPSIxMTYiPjxwIHN0eWxlPSJURVhULUFMSUdOOiBjZW50ZXI7IExJTkUtSEVJR0hUOiAyN3B4Ij48c3BhbiBzdHlsZT0iRk9OVC1GQU1JTFk6IOWui+S9kzsgRk9OVC1TSVpFOiAxNnB4Y29sb3I6IzAwMDAwMCI+5ouN5ZOB5omA5pyJ5Lq6PC9zcGFuPjwvcD48L3RkPjx0ZCBzdHlsZT0iQk9SREVSLUJPVFRPTTogYmxhY2sgMXB4IHNvbGlkOyBCT1JERVItTEVGVDogIzAwMDAwMDsgUEFERElORy1CT1RUT006IDBweDsgQkFDS0dST1VORC1DT0xPUjogdHJhbnNwYXJlbnQ7IFBBRERJTkctTEVGVDogNnB4OyBXSURUSDogNDQycHg7IFBBRERJTkctUklHSFQ6IDZweDsgSEVJR0hUOiA0MnB4OyBCT1JERVItVE9QOiAjMDAwMDAwOyBCT1JERVItUklHSFQ6IGJsYWNrIDFweCBzb2xpZDsgUEFERElORy1UT1A6IDBweCIgd2lkdGg9IjQ0MiIgY29sc3Bhbj0iMiI+PHAgc3R5bGU9IkxJTkUtSEVJR0hUOiAyN3B4OyBNQVJHSU4tVE9QOiBhdXRvOyBURVhULUlOREVOVDogMTNweDsgTUFSR0lOLUJPVFRPTTogYXV0byI+PHNwYW4gc3R5bGU9ImNvbG9yOiMwMDAwMDAiPjxzcGFuIHN0eWxlPSJGT05ULUZBTUlMWTog5a6L5L2TOyBGT05ULVNJWkU6IDE2cHgiPjxzcGFuPiZuYnNwOzwvc3Bhbj48L3NwYW4+PHNwYW4gc3R5bGU9IkZPTlQtRkFNSUxZOiDlrovkvZM7IEZPTlQtU0laRTogMTZweCI+6KKr5omn6KGM5Lq677ya5byg5p2wPC9zcGFuPjwvc3Bhbj48L3A+PC90ZD48L3RyPjx0ciBzdHlsZT0iSEVJR0hUOiAzN3B4Ij48dGQgc3R5bGU9IkJPUkRFUi1CT1RUT006IHdpbmRvd3RleHQgMXB4IHNvbGlkOyBCT1JERVItTEVGVDogYmxhY2sgMXB4IHNvbGlkOyBQQURESU5HLUJPVFRPTTogMHB4OyBCQUNLR1JPVU5ELUNPTE9SOiB0cmFuc3BhcmVudDsgUEFERElORy1MRUZUOiAwcHg7IFdJRFRIOiAxMTZweDsgUEFERElORy1SSUdIVDogMHB4OyBIRUlHSFQ6IDM3cHg7IEJPUkRFUi1UT1A6ICMwMDAwMDA7IEJPUkRFUi1SSUdIVDogYmxhY2sgMXB4IHNvbGlkOyBQQURESU5HLVRPUDogMHB4IiByb3dzcGFuPSIzIiB3aWR0aD0iMTE2Ij48cCBzdHlsZT0iVEVYVC1BTElHTjogY2VudGVyOyBMSU5FLUhFSUdIVDogMjdweCI+PHNwYW4gc3R5bGU9IkZPTlQtRkFNSUxZOiDlrovkvZM7IEZPTlQtU0laRTogMTZweGNvbG9yOiMwMDAwMDAiPuaLjeWTgeeOsOeKtjwvc3Bhbj48L3A+PC90ZD48dGQgc3R5bGU9IkJPUkRFUi1CT1RUT006IGJsYWNrIDFweCBzb2xpZDsgQk9SREVSLUxFRlQ6ICMwMDAwMDA7IFBBRERJTkctQk9UVE9NOiAwcHg7IEJBQ0tHUk9VTkQtQ09MT1I6IHRyYW5zcGFyZW50OyBQQURESU5HLUxFRlQ6IDZweDsgV0lEVEg6IDE0MnB4OyBQQURESU5HLVJJR0hUOiA2cHg7IEhFSUdIVDogMzdweDsgQk9SREVSLVRPUDogIzAwMDAwMDsgQk9SREVSLVJJR0hUOiBibGFjayAxcHggc29saWQ7IFBBRERJTkctVE9QOiAwcHgiIHdpZHRoPSIxNDIiPjxwIHN0eWxlPSJURVhULUFMSUdOOiBjZW50ZXI7IExJTkUtSEVJR0hUOiAyN3B4OyBNQVJHSU4tVE9QOiBhdXRvOyBURVhULUlOREVOVDogMTZweDsgTUFSR0lOLUJPVFRPTTogYXV0byI+PHNwYW4gc3R5bGU9IkZPTlQtRkFNSUxZOiDlrovkvZM7IEZPTlQtU0laRTogMTZweGNvbG9yOiMwMDAwMDAiPuenn+i1geaDheWGtTwvc3Bhbj48L3A+PC90ZD48dGQgc3R5bGU9IkJPUkRFUi1CT1RUT006IGJsYWNrIDFweCBzb2xpZDsgQk9SREVSLUxFRlQ6ICMwMDAwMDA7IFBBRERJTkctQk9UVE9NOiAwcHg7IEJBQ0tHUk9VTkQtQ09MT1I6IHRyYW5zcGFyZW50OyBQQURESU5HLUxFRlQ6IDlweDsgV0lEVEg6IDMwMHB4OyBQQURESU5HLVJJR0hUOiA5cHg7IEhFSUdIVDogMzdweDsgQk9SREVSLVRPUDogIzAwMDAwMDsgQk9SREVSLVJJR0hUOiBibGFjayAxcHggc29saWQ7IFBBRERJTkctVE9QOiAwcHgiIHdpZHRoPSIzMDAiPjxwIHN0eWxlPSJURVhULUFMSUdOOiBjZW50ZXI7IExJTkUtSEVJR0hUOiAyN3B4OyBNQVJHSU4tVE9QOiBhdXRvOyBNQVJHSU4tQk9UVE9NOiBhdXRvIj48c3BhbiBzdHlsZT0iRk9OVC1GQU1JTFk6IOWui+S9kzsgRk9OVC1TSVpFOiAxNnB4Y29sb3I6IzAwMDAwMCI+6KKr5omn6KGM5Lq65bGF5L2PPC9zcGFuPjwvcD48L3RkPjwvdHI+PHRyIHN0eWxlPSJIRUlHSFQ6IDM3cHgiPjx0ZCBzdHlsZT0iQk9SREVSLUJPVFRPTTogYmxhY2sgMXB4IHNvbGlkOyBCT1JERVItTEVGVDogIzAwMDAwMDsgUEFERElORy1CT1RUT006IDBweDsgQkFDS0dST1VORC1DT0xPUjogdHJhbnNwYXJlbnQ7IFBBRERJTkctTEVGVDogNnB4OyBXSURUSDogMTQycHg7IFBBRERJTkctUklHSFQ6IDZweDsgSEVJR0hUOiAzN3B4OyBCT1JERVItVE9QOiAjMDAwMDAwOyBCT1JERVItUklHSFQ6IGJsYWNrIDFweCBzb2xpZDsgUEFERElORy1UT1A6IDBweCIgd2lkdGg9IjE0MiI+PHAgc3R5bGU9IlRFWFQtQUxJR046IGNlbnRlcjsgTElORS1IRUlHSFQ6IDI3cHg7IE1BUkdJTi1UT1A6IGF1dG87IFRFWFQtSU5ERU5UOiAxNnB4OyBNQVJHSU4tQk9UVE9NOiBhdXRvIj48c3BhbiBzdHlsZT0iRk9OVC1GQU1JTFk6IOWui+S9kzsgRk9OVC1TSVpFOiAxNnB4Y29sb3I6IzAwMDAwMCI+6ZKl5YyZPC9zcGFuPjwvcD48L3RkPjx0ZCBzdHlsZT0iQk9SREVSLUJPVFRPTTogYmxhY2sgMXB4IHNvbGlkOyBCT1JERVItTEVGVDogIzAwMDAwMDsgUEFERElORy1CT1RUT006IDBweDsgQkFDS0dST1VORC1DT0xPUjogdHJhbnNwYXJlbnQ7IFBBRERJTkctTEVGVDogNnB4OyBXSURUSDogMzAwcHg7IFBBRERJTkctUklHSFQ6IDZweDsgSEVJR0hUOiAzN3B4OyBCT1JERVItVE9QOiAjMDAwMDAwOyBCT1JERVItUklHSFQ6IGJsYWNrIDFweCBzb2xpZDsgUEFERElORy1UT1A6IDBweCIgd2lkdGg9IjMwMCI+PHAgc3R5bGU9IlRFWFQtQUxJR046IGNlbnRlcjsgTElORS1IRUlHSFQ6IDI3cHg7IE1BUkdJTi1UT1A6IGF1dG87IE1BUkdJTi1CT1RUT006IGF1dG8iPjxzcGFuIHN0eWxlPSJGT05ULUZBTUlMWTog5a6L5L2TOyBGT05ULVNJWkU6IDE2cHhjb2xvcjojMDAwMDAwIj7ooqvmiafooYzkurrlpIQ8L3NwYW4+PC9wPjwvdGQ+PC90cj48dHIgc3R5bGU9IkhFSUdIVDogMzdweCI+PHRkIHN0eWxlPSJCT1JERVItQk9UVE9NOiBibGFjayAxcHggc29saWQ7IEJPUkRFUi1MRUZUOiAjMDAwMDAwOyBQQURESU5HLUJPVFRPTTogMHB4OyBCQUNLR1JPVU5ELUNPTE9SOiB0cmFuc3BhcmVudDsgUEFERElORy1MRUZUOiA2cHg7IFdJRFRIOiAxNDJweDsgUEFERElORy1SSUdIVDogNnB4OyBIRUlHSFQ6IDM3cHg7IEJPUkRFUi1UT1A6ICMwMDAwMDA7IEJPUkRFUi1SSUdIVDogYmxhY2sgMXB4IHNvbGlkOyBQQURESU5HLVRPUDogMHB4IiB3aWR0aD0iMTQyIj48cCBzdHlsZT0iVEVYVC1BTElHTjogY2VudGVyOyBMSU5FLUhFSUdIVDogMjdweDsgTUFSR0lOLVRPUDogYXV0bzsgVEVYVC1JTkRFTlQ6IDE2cHg7IE1BUkdJTi1CT1RUT006IGF1dG8iPjxzcGFuIHN0eWxlPSJGT05ULUZBTUlMWTog5a6L5L2TOyBGT05ULVNJWkU6IDE2cHhjb2xvcjojMDAwMDAwIj7miLflj6M8L3NwYW4+PC9wPjwvdGQ+PHRkIHN0eWxlPSJCT1JERVItQk9UVE9NOiBibGFjayAxcHggc29saWQ7IEJPUkRFUi1MRUZUOiAjMDAwMDAwOyBQQURESU5HLUJPVFRPTTogMHB4OyBCQUNLR1JPVU5ELUNPTE9SOiB0cmFuc3BhcmVudDsgUEFERElORy1MRUZUOiA2cHg7IFdJRFRIOiAzMDBweDsgUEFERElORy1SSUdIVDogNnB4OyBIRUlHSFQ6IDM3cHg7IEJPUkRFUi1UT1A6ICMwMDAwMDA7IEJPUkRFUi1SSUdIVDogYmxhY2sgMXB4IHNvbGlkOyBQQURESU5HLVRPUDogMHB4IiB3aWR0aD0iMzAwIj48cCBzdHlsZT0iVEVYVC1BTElHTjogY2VudGVyOyBMSU5FLUhFSUdIVDogMjdweDsgTUFSR0lOLVRPUDogYXV0bzsgTUFSR0lOLUJPVFRPTTogYXV0byI+PHNwYW4gc3R5bGU9IkZPTlQtRkFNSUxZOiDlrovkvZM7IEZPTlQtU0laRTogMTZweGNvbG9yOiMwMDAwMDAiPuaciTwvc3Bhbj48L3A+PC90ZD48L3RyPjx0ciBzdHlsZT0iSEVJR0hUOiA4MnB4Ij48dGQgc3R5bGU9IkJPUkRFUi1CT1RUT006IGJsYWNrIDFweCBzb2xpZDsgQk9SREVSLUxFRlQ6IGJsYWNrIDFweCBzb2xpZDsgUEFERElORy1CT1RUT006IDBweDsgQkFDS0dST1VORC1DT0xPUjogdHJhbnNwYXJlbnQ7IFBBRERJTkctTEVGVDogNnB4OyBXSURUSDogMTE2cHg7IFBBRERJTkctUklHSFQ6IDZweDsgSEVJR0hUOiA4MnB4OyBCT1JERVItVE9QOiAjMDAwMDAwOyBCT1JERVItUklHSFQ6IGJsYWNrIDFweCBzb2xpZDsgUEFERElORy1UT1A6IDBweCIgd2lkdGg9IjExNiI+PHAgc3R5bGU9IlRFWFQtQUxJR046IGNlbnRlcjsgTElORS1IRUlHSFQ6IDI3cHgiPjxzcGFuIHN0eWxlPSJGT05ULUZBTUlMWTog5a6L5L2TOyBGT05ULVNJWkU6IDE2cHhjb2xvcjojMDAwMDAwIj7mnYPliKnpmZDliLbmg4XlhrXlj4rnkZXnlrXmg4XlhrU8L3NwYW4+PC9wPjwvdGQ+PHRkIHN0eWxlPSJCT1JERVItQk9UVE9NOiBibGFjayAxcHggc29saWQ7IEJPUkRFUi1MRUZUOiAjMDAwMDAwOyBQQURESU5HLUJPVFRPTTogMHB4OyBCQUNLR1JPVU5ELUNPTE9SOiB0cmFuc3BhcmVudDsgUEFERElORy1MRUZUOiA2cHg7IFdJRFRIOiA0NDJweDsgUEFERElORy1SSUdIVDogNnB4OyBIRUlHSFQ6IDgycHg7IEJPUkRFUi1UT1A6ICMwMDAwMDA7IEJPUkRFUi1SSUdIVDogYmxhY2sgMXB4IHNvbGlkOyBQQURESU5HLVRPUDogMHB4IiB3aWR0aD0iNDQyIiBjb2xzcGFuPSIyIj48cCBzdHlsZT0iVEVYVC1JTkRFTlQ6IDBweCI+PHNwYW4gc3R5bGU9ImNvbG9yOiMwMDAwMDAiPjxzcGFuIHN0eWxlPSJGT05ULUZBTUlMWTog5a6L5L2TOyBGT05ULVNJWkU6IDE2cHgiPjxzcGFuPjEuPHNwYW4gc3R5bGU9IkZPTlQ6IDlweCAmIzM5O1RpbWVzIE5ldyBSb21hbiYjMzk7Ij4mbmJzcDsmbmJzcDsgPC9zcGFuPjwvc3Bhbj48L3NwYW4+PHNwYW4gc3R5bGU9IkZPTlQtRkFNSUxZOiDlrovkvZM7IEZPTlQtU0laRTogMTZweCI+55Sx5LmM6bKB5pyo6b2Q5biC5aSp5bGx5Yy65Lq65rCR5rOV6Zmi5omn6KGM77ybPC9zcGFuPjwvc3Bhbj48L3A+PHA+PHNwYW4gc3R5bGU9ImNvbG9yOiMwMDAwMDAiPjxzcGFuIHN0eWxlPSJGT05ULUZBTUlMWTog5a6L5L2TOyBGT05ULVNJWkU6IDE2cHgiPjI8L3NwYW4+PHNwYW4gc3R5bGU9IkZPTlQtRkFNSUxZOiDlrovkvZM7IEZPTlQtU0laRTogMTZweCI+44CB5qyg5pqW5rCU6LS56LSw5bm0PC9zcGFuPjwvc3Bhbj48L3A+PC90ZD48L3RyPjx0ciBzdHlsZT0iSEVJR0hUOiA4MXB4Ij48dGQgc3R5bGU9IkJPUkRFUi1CT1RUT006IGJsYWNrIDFweCBzb2xpZDsgQk9SREVSLUxFRlQ6IGJsYWNrIDFweCBzb2xpZDsgUEFERElORy1CT1RUT006IDBweDsgQkFDS0dST1VORC1DT0xPUjogdHJhbnNwYXJlbnQ7IFBBRERJTkctTEVGVDogNnB4OyBXSURUSDogMTE2cHg7IFBBRERJTkctUklHSFQ6IDZweDsgSEVJR0hUOiA4MXB4OyBCT1JERVItVE9QOiAjMDAwMDAwOyBCT1JERVItUklHSFQ6IGJsYWNrIDFweCBzb2xpZDsgUEFERElORy1UT1A6IDBweCIgd2lkdGg9IjExNiI+PHAgc3R5bGU9IlRFWFQtQUxJR046IGNlbnRlcjsgTElORS1IRUlHSFQ6IDI3cHgiPjxzcGFuIHN0eWxlPSJGT05ULUZBTUlMWTog5a6L5L2TOyBGT05ULVNJWkU6IDE2cHhjb2xvcjojMDAwMDAwIj7miJDkuqTlkI7mj5DkvpvnmoTmlofku7Y8L3NwYW4+PC9wPjwvdGQ+PHRkIHN0eWxlPSJCT1JERVItQk9UVE9NOiBibGFjayAxcHggc29saWQ7IEJPUkRFUi1MRUZUOiAjMDAwMDAwOyBQQURESU5HLUJPVFRPTTogMHB4OyBCQUNLR1JPVU5ELUNPTE9SOiB0cmFuc3BhcmVudDsgUEFERElORy1MRUZUOiA2cHg7IFdJRFRIOiA0NDJweDsgUEFERElORy1SSUdIVDogNnB4OyBIRUlHSFQ6IDgxcHg7IEJPUkRFUi1UT1A6ICMwMDAwMDA7IEJPUkRFUi1SSUdIVDogYmxhY2sgMXB4IHNvbGlkOyBQQURESU5HLVRPUDogMHB4IiB3aWR0aD0iNDQyIiBjb2xzcGFuPSIyIj48cCBzdHlsZT0iVEVYVC1JTkRFTlQ6IDEycHgiPjxzcGFuIHN0eWxlPSJGT05ULUZBTUlMWTog5a6L5L2TOyBDT0xPUjogIzExMTExMSI+MTwvc3Bhbj48c3BhbiBzdHlsZT0iRk9OVC1GQU1JTFk6IOWui+S9kzsgQ09MT1I6ICMxMTExMTEiPuOAgeOAiuazlemZouijgeWumuS5puOAi++8mzwvc3Bhbj48L3A+PHAgc3R5bGU9IlRFWFQtSU5ERU5UOiAxMnB4Ij48c3BhbiBzdHlsZT0iRk9OVC1GQU1JTFk6IOWui+S9kzsgQ09MT1I6ICMxMTExMTEiPjI8L3NwYW4+PHNwYW4gc3R5bGU9IkZPTlQtRkFNSUxZOiDlrovkvZM7IENPTE9SOiAjMTExMTExIj7jgIHjgIrmi43ljZbmiJDkuqTnoa7orqTkuabjgIvvvJs8L3NwYW4+PC9wPjxwIHN0eWxlPSJURVhULUlOREVOVDogMTJweCI+PHNwYW4gc3R5bGU9IkZPTlQtRkFNSUxZOiDlrovkvZM7IENPTE9SOiAjMTExMTExIj4zPC9zcGFuPjxzcGFuIHN0eWxlPSJGT05ULUZBTUlMWTog5a6L5L2TOyBDT0xPUjogIzExMTExMSI+44CB44CK5Y2P5Yqp5omn6KGM6YCa55+l5Lmm44CL44CCPC9zcGFuPjwvcD48L3RkPjwvdHI+PHRyIHN0eWxlPSJIRUlHSFQ6IDI1OXB4Ij48dGQgc3R5bGU9IkJPUkRFUi1CT1RUT006IGJsYWNrIDFweCBzb2xpZDsgQk9SREVSLUxFRlQ6IGJsYWNrIDFweCBzb2xpZDsgUEFERElORy1CT1RUT006IDBweDsgQkFDS0dST1VORC1DT0xPUjogdHJhbnNwYXJlbnQ7IFBBRERJTkctTEVGVDogNnB4OyBXSURUSDogNTU4cHg7IFBBRERJTkctUklHSFQ6IDZweDsgSEVJR0hUOiAyNTlweDsgQk9SREVSLVRPUDogIzAwMDAwMDsgQk9SREVSLVJJR0hUOiBibGFjayAxcHggc29saWQ7IFBBRERJTkctVE9QOiAwcHgiIHdpZHRoPSI1NTgiIGNvbHNwYW49IjMiPjxwIHN0eWxlPSJURVhULUFMSUdOOiBjZW50ZXI7IExJTkUtSEVJR0hUOiAyN3B4Ij48c3Ryb25nPjxzcGFuIHN0eWxlPSJGT05ULUZBTUlMWTog5a6L5L2TOyBDT0xPUjogYmxhY2s7IEZPTlQtU0laRTogMTZweCI+5ouN5ZOB5LuL57uNPC9zcGFuPjwvc3Ryb25nPjxzdHJvbmc+PC9zdHJvbmc+PC9wPjxwIHN0eWxlPSJURVhULUFMSUdOOiBsZWZ0OyBMSU5FLUhFSUdIVDogMjdweDsgVEVYVC1JTkRFTlQ6IDM1cHgiPjxzcGFuIHN0eWxlPSJjb2xvcjojMDAwMDAwIj48c3Ryb25nPjxzcGFuIHN0eWxlPSJGT05ULUZBTUlMWTog5a6L5L2TOyBGT05ULVNJWkU6IDE2cHgiPjE8L3NwYW4+PC9zdHJvbmc+PHNwYW4gc3R5bGU9IkZPTlQtRkFNSUxZOiDlrovkvZM7IEZPTlQtU0laRTogMTZweCI+44CB5ouN5Y2W5qCH55qE5p2D5bGe54q25Ya177yaPC9zcGFuPjwvc3Bhbj48L3A+PHAgc3R5bGU9IlRFWFQtQUxJR046IGxlZnQ7IExJTkUtSEVJR0hUOiAyN3B4OyBURVhULUlOREVOVDogMzZweCI+PHNwYW4gc3R5bGU9IkZPTlQtRkFNSUxZOiDlrovkvZM7IEZPTlQtU0laRTogMTZweGNvbG9yOiMwMDAwMDAiPu+8iDxzcGFuPjE8L3NwYW4+77yJ5oi/5Lqn77ya5L2N5LqO5LmM6bKB5pyo6b2Q5biC5aSp5bGx5Yy657+g5rOJ6LevPHNwYW4+NjE0PC9zcGFuPuWPt+mygeePrea4qemmqOiKseWbreWwj+WMujxzcGFuPjI8L3NwYW4+5qCLPHNwYW4+Njwvc3Bhbj7lsYI8c3Bhbj4yPC9zcGFuPuWNleWFgzxzcGFuPjYwMTwvc3Bhbj7lrqTjgJDmiL/kuqfor4Hlj7fvvJo8c3Bhbj4yMDEyNDA4MTE3PC9zcGFuPuOAke+8jOOAkOWcn+WcsOivge+8muS5jOW4guWbveeUqO+8iDxzcGFuPjIwMTI8L3NwYW4+77yJ56ysPHNwYW4+MDAwMjg0MTk8L3NwYW4+5Y+344CR77yM5oC75bGC5YWxPHNwYW4+Njwvc3Bhbj7lsYLvvIzmoIfnmoTkvY3kuo7nrKw8c3Bhbj42PC9zcGFuPuWxgu+8jOWkjeW8j+OAguaAp+i0qO+8muS9j+Wuhe+8jOeZu+iusOW7uuetkemdouenrzxzcGFuPjE0OC4wMjwvc3Bhbj7jjqHjgII8L3NwYW4+PC9wPjxwIHN0eWxlPSJURVhULUFMSUdOOiBsZWZ0OyBMSU5FLUhFSUdIVDogMjdweDsgVEVYVC1JTkRFTlQ6IDM2cHgiPjxzcGFuIHN0eWxlPSJGT05ULUZBTUlMWTog5a6L5L2TOyBGT05ULVNJWkU6IDE2cHhjb2xvcjojMDAwMDAwIj7vvIg8c3Bhbj4yPC9zcGFuPu+8ieeUseS5jOmygeacqOm9kOW4guWkqeWxseWMuuS6uuawkeazlemZouaJp+ihjOOAgjwvc3Bhbj48L3A+PHAgc3R5bGU9IlRFWFQtQUxJR046IGxlZnQ7IExJTkUtSEVJR0hUOiAyN3B4OyBURVhULUlOREVOVDogMzZweCI+PHNwYW4gc3R5bGU9ImNvbG9yOiMwMDAwMDAiPjxzdHJvbmc+PHNwYW4gc3R5bGU9IkZPTlQtRkFNSUxZOiDlrovkvZM7IEZPTlQtU0laRTogMTZweCI+Mjwvc3Bhbj48L3N0cm9uZz48c3BhbiBzdHlsZT0iRk9OVC1GQU1JTFk6IOWui+S9kzsgRk9OVC1TSVpFOiAxNnB4Ij7jgIHmoIfnmoTkvY3nva7vvJo8L3NwYW4+PC9zcGFuPjwvcD48cCBzdHlsZT0iVEVYVC1BTElHTjogbGVmdDsgTElORS1IRUlHSFQ6IDI3cHg7IFRFWFQtSU5ERU5UOiAzNnB4Ij48c3BhbiBzdHlsZT0iRk9OVC1GQU1JTFk6IOWui+S9kzsgRk9OVC1TSVpFOiAxNnB4Y29sb3I6IzAwMDAwMCI+5L2N5LqO5L2N5LqO5LmM6bKB5pyo6b2Q5biC5aSp5bGx5Yy657+g5rOJ6LevPHNwYW4+NjE0PC9zcGFuPuWPt+mygeePrea4qemmqOiKseWbreWwj+WMujxzcGFuPjI8L3NwYW4+5qCLPHNwYW4+Njwvc3Bhbj7lsYI8c3Bhbj4yPC9zcGFuPuWNleWFgzxzcGFuPjYwMTwvc3Bhbj7lrqTmiL/lnLDkuqfvvIzpgrvnv6Dms4not6/vvIzot53nv6Dms4not688c3Bhbj4xMDA8L3NwYW4+57Gz44CC5ZGo6L655YiG5biD54mp5Lia5Y2V5L2N5pyJ5bmz5a6J5YW05pe65L2P5a6F5bCP5Yy644CB54Wk5YyW5Y6C5a625bGe6Zmi44CB6Zuq5bOw5L2P5a6F5bCP5Yy644CB56m65Lit6Iqx5Zut5L2P5a6F5bCP5Yy644CB5LmM6bKB5pyo6b2Q5biC56ysPHNwYW4+NDk8L3NwYW4+5Lit5a2m562J44CCPC9zcGFuPjwvcD48cCBzdHlsZT0iVEVYVC1JTkRFTlQ6IDMycHg7IE1BUkdJTi1MRUZUOiAwcHgiPjxzcGFuIHN0eWxlPSJGT05ULUZBTUlMWTog5a6L5L2TOyBDT0xPUjogYmxhY2siPjxzcGFuPjPjgIE8L3NwYW4+PC9zcGFuPjxzcGFuIHN0eWxlPSJjb2xvcjojMDAwMDAwIj48c3BhbiBzdHlsZT0iRk9OVC1GQU1JTFk6IOWui+S9kyI+5Lqk6YCa54q25Ya177ya6ZmE6L+R5YWs5Lqk5pyJPC9zcGFuPjxzcGFuIHN0eWxlPSJ1bmRlZmluZWRmb250LWZhbWlseTpUaW1lcyBOZXcgUm9tYW4iPjM2PC9zcGFuPjxzcGFuIHN0eWxlPSJGT05ULUZBTUlMWTog5a6L5L2TIj7ot6/jgIE8L3NwYW4+PHNwYW4gc3R5bGU9InVuZGVmaW5lZGZvbnQtZmFtaWx5OlRpbWVzIE5ldyBSb21hbiI+ODAxPC9zcGFuPjxzcGFuIHN0eWxlPSJGT05ULUZBTUlMWTog5a6L5L2TIj7ot6/jgIE8L3NwYW4+PHNwYW4gc3R5bGU9InVuZGVmaW5lZGZvbnQtZmFtaWx5OlRpbWVzIE5ldyBSb21hbiI+OTAyPC9zcGFuPjxzcGFuIHN0eWxlPSJGT05ULUZBTUlMWTog5a6L5L2TIj7ot6/jgIE8L3NwYW4+PHNwYW4gc3R5bGU9InVuZGVmaW5lZGZvbnQtZmFtaWx5OlRpbWVzIE5ldyBSb21hbiI+OTE5PC9zcGFuPjxzcGFuIHN0eWxlPSJGT05ULUZBTUlMWTog5a6L5L2TIj7ot6/nrYnlhazkuqTnur/ot6/vvIzlj6/nm7Tovr7miJbkuK3ovazoh7PlhajluILlkITlpITvvIzlr7nlpJbkuqTpgJrkvr/liKnjgII8L3NwYW4+PC9zcGFuPjwvcD48cCBzdHlsZT0iVEVYVC1BTElHTjogbGVmdDsgTElORS1IRUlHSFQ6IDI3cHg7IFRFWFQtSU5ERU5UOiAzMnB4Ij48c3BhbiBzdHlsZT0iY29sb3I6IzAwMDAwMCI+PHN0cm9uZz48c3BhbiBzdHlsZT0iRk9OVC1GQU1JTFk6IOWui+S9kzsgRk9OVC1TSVpFOiAxNnB4Ij40PC9zcGFuPjwvc3Ryb25nPjxzcGFuIHN0eWxlPSJGT05ULUZBTUlMWTog5a6L5L2TOyBGT05ULVNJWkU6IDE2cHgiPuOAgeeJueauiuivtOaYju+8mjwvc3Bhbj48L3NwYW4+PHNwYW4gc3R5bGU9IkZPTlQtRkFNSUxZOiDlrovkvZM7IENPTE9SOiAjMTExMTExOyBGT05ULVNJWkU6IDE2cHgiPigxKTwvc3Bhbj48c3BhbiBzdHlsZT0iRk9OVC1GQU1JTFk6IOWui+S9kzsgQ09MT1I6ICMxMTExMTE7IEZPTlQtU0laRTogMTZweCI+5oi/5Lqn6L+H5oi35Lqk5piT6L+H56iL5Lit5omA5Y+R55Sf55qE56iO6LS55YWo6YOo55Sx5Lmw5Y+X5Lq65om/5ouF77yM5qCH55qE5rC044CB55S144CB54Wk54mp5Lia566h55CG6LS55qyg6LS55LiN6K+m77yM5aaC5qyg6LS55Z2H5pyJ5Lmw5Y+X5Lq65om/5ouF44CC77yIPHNwYW4+Mjwvc3Bhbj7vvInnq57kubDkurrpnIDoh6rooYzkuobop6Pmi43ljZbmoIfnmoTniannjrDnirbvvIzms5XpmaLmjInnjrDnirbov5vooYzmi43ljZbjgII8c3Bhbj4oMyk8L3NwYW4+5Lmw5Y+X5Lq66LWE5qC86Zeu6aKY77yM6K+35Lmw5Y+X5Lq66Ieq6KGM5ZCR55u45YWz6YOo6Zeo6L+b6KGM5LqG6Kej77yM5ZCm5YiZ5Lmw5Y+X5Lq66aG76Ieq6KGM5om/5ouF5Zug5Li75L2T5LiN6YCC5ZCI6YCg5oiQ55qE5omA5pyJ5p2D5peg5rOV6L2s56e7562J6aOO6Zmp44CCPHNwYW4+KDQpPC9zcGFuPuagh+eahOeJqeWmguacieWFtuWug+makOaAp+eRleeWte+8jOWdh+eUseS5sOWPl+S6uuaJv+aLheOAgjxzcGFuPig1KTwvc3Bhbj7mi43ljZbmoIfnmoTlhoXnmoTlrrblhbfjgIHlrrbnlLXjgIHlj4rlj6/np7vliqg8c3BhbiBzdHlsZT0iQkFDS0dST1VORDogd2hpdGUiPueJqeWTgeS4jeWMheaLrOWcqOaLjeWNluiMg+WbtOWGheOAgjwvc3Bhbj48L3NwYW4+PC9wPjxwIHN0eWxlPSJURVhULUFMSUdOOiBsZWZ0OyBMSU5FLUhFSUdIVDogMjdweDsgVEVYVC1JTkRFTlQ6IDMycHgiPjxzcGFuIHN0eWxlPSJGT05ULUZBTUlMWTog5a6L5L2TOyBDT0xPUjogYmxhY2s7IEZPTlQtU0laRTogMTZweCI+Jm5ic3A7PC9zcGFuPjwvcD48L3RkPjwvdHI+PC90Ym9keT48L3RhYmxlPjxwIHN0eWxlPSJURVhULUFMSUdOOiBjZW50ZXI7IExJTkUtSEVJR0hUOiAyN3B4Ij48c3BhbiBzdHlsZT0iRk9OVC1GQU1JTFk6IOWui+S9kzsgQ09MT1I6ICMxMTExMTE7IEZPTlQtU0laRTogMTZweCI+PHNwYW4gc3R5bGU9IkJBQ0tHUk9VTkQ6IHdoaXRlIj48L3NwYW4+PC9zcGFuPiZuYnNwOzwvcD48L3RkPjwvdHI+PC90Ym9keT48L3RhYmxlPjwvZGl2PjxwPjwvcD4=";
			try {
				byte[] c=Base64.decode(info);
				System.out.println(new String(c));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}  
