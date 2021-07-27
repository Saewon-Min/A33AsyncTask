package com.kosmo.a33asynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "KOSMO123";

    ProgressBar mprogress1;
    int mProgressStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mprogress1 = findViewById(R.id.progressBar1);
    }

    public void onBtnClicked(View v){
        new CounterTask().execute(0);
    }

    /*
    AsyncTask
        : 작업 쓰레드와 관련된 복잡한 작업을 쉽게 처리해주는 클래스이다.
        UI에 대한 비동기 작업을 자동으로 실행해주며 개발자가 직접 핸들러
        클래스를 만들 필요가 없다. 실행은 execute() 메소드를 호출하면 된다.
        전달할 파라미터가 있는 경우  execute(파라미터) 형식으로 사용한다.

        형식]
            AsyncTask<Param , Progress , Result>
                Param : 실행시 작업에 전달 되는 값(파라미터)의 타입을 지정
                Progress : 작업의 진행 정도를 나타내는 값의 타입을 지정
                Result : 작업의 결과값을 나타내는 타입을 지정

            만약 사용할 필요가 없는 타입이 있다면 Void라고 표기한다.(V는 대문자여야함)
            또한 모든 매개변수는 가변인자를 사용하여 여러개의 파라미터를
            처리할 수 있도록 정의되어 있다.
     */
    class CounterTask extends AsyncTask<Integer,Integer,Integer>{

        // doInBackground 메소드 실행 전에 호출되는 메소드
        protected void onPreExecute(){
            Log.i(TAG, "onPreExecute() 실행");

        }
        /*
        execute() 가 호출되면 자동으로 doInBackground()가 호출된다.
        해당 클래스에서 실제 작업을 담당하는 메소드이다.
         */
        protected  Integer doInBackground(Integer... value){
            while(mProgressStatus<100){
                try{
                    // 0.1초에 한번씩 block 상태로 들어간다.
                    Thread.sleep(100);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                mProgressStatus++;
                // onProgressUpdate() 을 호출하는 함수로 진행 상황에 대한 표현을 한다.
                publishProgress(mProgressStatus);
            }
            return mProgressStatus;
        }

        /*
        onProgressUpdate
            : doInBackground() 메소드 실행 중 언제든 호출할 수 있는 메소드로
              해당 메소드를 호출할때는 publishProgress() 를 사용한다.
         */
        protected  void onProgressUpdate(Integer... value){
            // progressBar 의 진행 상황을 설정한다.
            mprogress1.setProgress(mProgressStatus);
        }

        /*
        onPostExecute
            : doInBackground() 메소드가 실행된 후 결과값을 해당 메소드로 전송한다.
              즉, 정상적으로 종료되었을때 호출된다.
         */
        protected  void onPostExecute(Integer result){
            mprogress1.setProgress(mProgressStatus);
        }
    }
}