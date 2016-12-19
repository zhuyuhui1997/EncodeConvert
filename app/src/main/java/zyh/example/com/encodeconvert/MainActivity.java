package zyh.example.com.encodeconvert;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {
    private Button btn_convert;
    private Button btn_clear;
    private EditText edit_unicode;
    private EditText edit_utf_8;
    private EditText edit_Chinese;

    private String str_edit_unicode;
    private String str_edit_utf_8;
    private String str_edit_Chinese;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        btn_convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startConvert();
            }
        });
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearText();
            }
        });
    }

    public void init() {
        btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_convert = (Button) findViewById(R.id.btn_convert);
        edit_Chinese = (EditText) findViewById(R.id.edit_Chinese);
        edit_unicode = (EditText) findViewById(R.id.edit_Unicode);
        edit_utf_8 = (EditText) findViewById(R.id.edit_utf_8);
    }

    public void startConvert() {
        getEditString();
        checkNULLString();
        convert();

    }

    public void checkNULLString() {
        if (str_edit_Chinese .length()==0 && str_edit_utf_8 .length()==0
                && str_edit_unicode.length()==0)
            Toast.makeText(MainActivity.this, "请在中文或unicode或utf-8输入字符", Toast.LENGTH_SHORT).show();
    }

    public void getEditString() {
        str_edit_Chinese = edit_Chinese.getText().toString();
        str_edit_unicode = edit_unicode.getText().toString();
        str_edit_utf_8 = edit_utf_8.getText().toString();

    }

    public void clearText() {

        edit_utf_8.setText("");
        edit_unicode.setText("");
        edit_Chinese.setText("");

    }

    public void convert() {
        if (str_edit_unicode.length()>0) {
            String str_edit_unicode_num=str_edit_unicode.substring(2);
            char char_edit_chinese=(char)Integer.parseInt(str_edit_unicode_num,16);
            StringBuffer stringBuffer=new StringBuffer();
            stringBuffer.append(char_edit_chinese);
            edit_Chinese.setText(stringBuffer.toString());
            try {
               byte[] b= edit_Chinese.getText().toString().getBytes("utf-8");
                StringBuffer stringBuffer1=new StringBuffer();
                stringBuffer1.append(Integer.toHexString((b[0]&0xff)));
                stringBuffer1.append(",");
                stringBuffer1.append(Integer.toHexString((b[1])&0xff));
                stringBuffer1.append(",");
                stringBuffer1.append(Integer.toHexString((b[2])&0xff));
                edit_utf_8.setText(stringBuffer1);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }


        if (str_edit_unicode.length()<=0 && str_edit_utf_8 .length()>0
                ) {
            try {
               String[] s=str_edit_utf_8.split("\\,");;
                byte[] bb=new byte[3];
                bb[0]=(byte)Integer.parseInt(s[0],16);
                bb[1]=(byte)Integer.parseInt(s[1],16);
                 bb[2]=(byte)Integer.parseInt(s[2],16);
                str_edit_Chinese=new String(bb,"utf-8");
                edit_Chinese.setText(str_edit_Chinese);
                char c=str_edit_Chinese.charAt(0);
                edit_unicode.setText("\\u"+Integer.toHexString(c));




            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


        }
        if (str_edit_unicode.length()<=0&&str_edit_utf_8.length()<=0&&str_edit_Chinese.length()>0)
        {



        }





    }

       public String chinese2utf8(String str_edit_Chinese)
       {
           StringBuffer stringBuffer1=new StringBuffer();
           try {
               byte[] b=str_edit_Chinese.getBytes("utf-8");

               stringBuffer1.append(Integer.toHexString((b[0]&0xff)));
               stringBuffer1.append(",");
               stringBuffer1.append(Integer.toHexString((b[1])&0xff));
               stringBuffer1.append(",");
               stringBuffer1.append(Integer.toHexString((b[2])&0xff));
               edit_utf_8.setText(stringBuffer1);

       } catch (UnsupportedEncodingException e) {
               e.printStackTrace();
           }
           return  stringBuffer1.toString();  //返回utf-8字符串
       }
       public String chinese2Unicode(String str_edit_Chinese)
       {
          StringBuffer s=new StringBuffer();
           for (int i=0;i<str_edit_Chinese.length();i++)
           {
               char c=str_edit_Chinese.charAt(i);
               s.append("\\u"+Integer.toHexString(c));
               s.append(",");
           }
           edit_unicode.setText(s.toString());
           return  s.toString();  //返回unicode字符串

       }
        public  String unicode2chinese(String str_edit_unicode)
        {
            StringBuffer s=new StringBuffer();
            String[] hex=str_edit_unicode.split("\\\\u");
            for (int i=1;i<hex.length;i++)
            {
                int a=Integer.parseInt(hex[i],16);
                s.append((char)a);
            }
            edit_Chinese.setText(s.toString());
            return  s.toString();  //返回中文字符串

        }
}

