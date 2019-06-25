package net.sourceforge.simcpux;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONObject;

public class PayActivity extends Activity {

    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay);

        api = WXAPIFactory.createWXAPI(this, "wx8f894abe35fea216");

        Button appayBtn = (Button) findViewById(R.id.appay_btn);
        appayBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String url = "https://wxpay.wxutil.com/pub_v2/app/app_pay.php";
                Button payBtn = (Button) findViewById(R.id.appay_btn);
                payBtn.setEnabled(false);
                Toast.makeText(PayActivity.this, "获取订单中...", Toast.LENGTH_SHORT).show();
                try {
                    /*myAsynTask myAsynTask = new myAsynTask();
                    String content = myAsynTask.doInBackground(url);*/
//                    Toast.makeText(PayActivity.this, "获取到的支付信息:"+content, Toast.LENGTH_SHORT).show();
                    String content = "{\"appId\":\"wx8f894abe35fea216\",\"partnerId\":\"1900009211\",\"prepayId\":\"wx25140021434439265d0535b81900718600\",\"package\":\"Sign=WXPay\",\"timeStamp\":\"1561442421\",\"nonceStr\":\"154f4a4dd1024e19a0906289610daa7f\",\"paySign\":\"Urp6aeVxrE4qXDsWx/UaKvYkIwYtZj5i6Av/EprFOhk13fUVBW0ysJLTSrLSpBoY0e785ikb0kzmlONeMJg4jeDhdQ/x5/FcyNEtOh9/1OkUWcsns2WuRETKnKPIJlfAfvXp6kHca3ZHJIF9wAYsBaBIs9+A3hoVFzqFposZxKIigoCIfKrsnD7Tb9aQHw7E6AtFZ4gDh2UX+OFx4+3E5KSg/U32i+x76fwbqyeMauAFXCg6M9nwYDXBpjs4Bj04yDWBUIM16T4HWnc0v/GYcYuDjcOrXGvgxSnbKOlqgnWwj099NkoMN4WZp5+LfQ7Ma1tBkFYbLVzutrfxVTUulw==\"}";
                    if (content != null && content.length() > 0) {
                        Log.e("get server pay params:", content);
                        JSONObject json = new JSONObject(content);
                        if (!json.has("retcode")) {
                            PayReq req = new PayReq();
                            //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
                           /* req.appId = json.getString("appid");
                            req.partnerId = json.getString("partnerid");
                            req.prepayId = json.getString("prepayid");
                            req.nonceStr = json.getString("noncestr");
                            req.timeStamp = json.getString("timestamp");
                            req.packageValue = json.getString("package");
                            req.sign = json.getString("sign");
                            req.extData = "app data"; // optional*/
                            req.appId = json.getString("appId");
                            req.partnerId = json.getString("partnerId");
                            req.prepayId = json.getString("prepayId");
                            req.nonceStr = json.getString("nonceStr");
                            req.timeStamp = json.getString("timeStamp");
                            req.packageValue = json.getString("package");
                            req.sign = json.getString("paySign");
                            req.extData = "app data"; // optional
                            Toast.makeText(PayActivity.this, "正常调起支付", Toast.LENGTH_SHORT).show();
                            // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                            api.sendReq(req);
                        } else {
                            Log.d("PAY_GET", "返回错误" + json.getString("retmsg"));
                            Toast.makeText(PayActivity.this, "返回错误" + json.getString("retmsg"), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.d("PAY_GET", "服务器请求错误");
                        Toast.makeText(PayActivity.this, "服务器请求错误", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("PAY_GET", "异常：" + e.getMessage());
                    Toast.makeText(PayActivity.this, "异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                payBtn.setEnabled(true);
            }
        });
        Button checkPayBtn = (Button) findViewById(R.id.check_pay_btn);
        checkPayBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
                Toast.makeText(PayActivity.this, String.valueOf(isPaySupported), Toast.LENGTH_SHORT).show();
            }
        });

    }

    class myAsynTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            byte[] buf = Util.httpGet(strings[0]);
            if (buf != null && buf.length > 0) {
                return new String(buf);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //对布局的修改 放在这里做
        }
    }

}
