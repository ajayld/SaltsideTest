package saltside.example.com.saltside;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Ajay.Davanam on 2/9/2016.
 *
 * All networking calls to be added here. The notification is sent using
 * the ISSNetworkCallback interface.
 */
public class SSNetworkCalls {

    /**
     * Interface for notifying the result.
     */
    public interface ISSNetworkCallback {
        /**
         * Callback to notify the result of fetching the list of items
         * using the downloadListOfItems() method.
         *
         * @param jsonArray - the resulting list of items JSON array. Can be null if the
         *                      communication fails.
         */
        public void onListOfItemsCallback(final JSONArray jsonArray);
    };

    /**
     * Make the request to the backend server to fetch JSON array of items to be shown
     *
     * @param issNetworkCallback - Listener to be notified after the network call is complete.
     */
    public void downloadListOfItems(final ISSNetworkCallback issNetworkCallback) {

        /**
         * AsyncTask to download the JSON string from backend.
         */
        AsyncTask<String, Void, JSONArray> downloadTask = new AsyncTask<String, Void, JSONArray>() {
            /**
             * Override this method to perform a computation on a background thread. The
             * specified parameters are the parameters passed to {@link #execute}
             * by the caller of this task.
             * <p/>
             * This method can call {@link #publishProgress} to publish updates
             * on the UI thread.
             *
             * @param params The parameters of the task.
             * @return A result, defined by the subclass of this task.
             * @see #onPreExecute()
             * @see #onPostExecute
             * @see #publishProgress
             */
            @Override
            protected JSONArray doInBackground(String... params) {
                String strURL = params[0];
                String strResponse = "";

                URL url = null;
                try {
                    url = new URL(strURL);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                if (null != url) {
                    HttpsURLConnection conn = null;
                    try {
                        conn = (HttpsURLConnection) url.openConnection();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    conn.setConnectTimeout(SSConstants.CONN_TIMEOUT);
                    conn.setReadTimeout(SSConstants.HTTP_TIMEOUT);

                    // Allow Inputs
                    conn.setDoInput(true);

                    // Don't use a cached copy, to get the most recent data.
                    conn.setUseCaches(false);

                    try {
                        conn.setRequestMethod("GET");
                        conn.setRequestProperty("Content-Type", "application/json");

                        InputStream dataSteam = conn.getInputStream();

                        strResponse = readFromStream(dataSteam);

                        dataSteam.close();
                    } catch (ProtocolException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    conn.disconnect();
                }

                JSONArray jsonArray = parseResponse(strResponse);

                return jsonArray;
            }


            /**
             * <p>Runs on the UI thread after {@link #doInBackground}. The
             * specified result is the value returned by {@link #doInBackground}.</p>
             * <p/>
             * <p>This method won't be invoked if the task was cancelled.</p>
             *
             * @param jsonArray The result of the operation computed by {@link #doInBackground}.
             * @see #onPreExecute
             * @see #doInBackground
             * @see #onCancelled(Object)
             */
            @Override
            protected void onPostExecute(JSONArray jsonArray) {
                super.onPostExecute(jsonArray);

                if(null != issNetworkCallback) {
                    issNetworkCallback.onListOfItemsCallback(jsonArray);
                }
            }
        };

        // Execute the AsyncTask
        downloadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, SSConstants.HOST_NAME);
    }

    /**
     * Parse the JSON response.
     *
     * @param strResponse - the JSON response to be parsed.
     *
     * @return An instance of JSONArray. Null if it failed to parse.
     */
    private JSONArray parseResponse(String strResponse) {
        JSONArray jsonArray = null;

        try {
            jsonArray = new JSONArray(strResponse);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    /**
     * Read from the InputStream.
     *
     * @param dataSteam - the input stream to be read from.
     *
     * @return the String response read from dataStream
     */
    private String readFromStream(InputStream dataSteam) {
        String strResponse = "";

        byte[] buffer = new byte[SSConstants.READ_CHUNK_SIZE];
        int totalReadBytes = 0;
        int readBytes = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        do {
            try {
                readBytes = dataSteam.read(buffer, 0, SSConstants.READ_CHUNK_SIZE);

                if (readBytes > 0) {
                    totalReadBytes += readBytes;
                    bos.write(buffer, 0, readBytes);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        while (readBytes > 0);

        if (totalReadBytes > 0) {
            try {
                strResponse = new String(bos.toByteArray(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                strResponse = "";
            }
        }

        if(null != bos) {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return strResponse;
    }

}
