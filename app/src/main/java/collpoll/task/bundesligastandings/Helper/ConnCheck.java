package collpoll.task.bundesligastandings.Helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class ConnCheck {

    private Context context2;

    public ConnCheck(Context context){
        this.context2 = context;
    }

    public boolean Connect(){
        ConnectivityManager connectivity = (ConnectivityManager) context2.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for(int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }
}


