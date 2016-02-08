/*
 * Copyright (c) 2016. Albin Mathew
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.albinmathew.celluloid.api;

import android.support.annotation.NonNull;

import me.albinmathew.celluloid.api.base.BaseApi;
import me.albinmathew.celluloid.api.base.BaseResponseBean;
import retrofit.RetrofitError;

/**
 * The Api manager for all APIs.
 *
 * @author albin
 * @date 2 /2/16
 */
public class ApiManager {

    private static ApiManager sApiManager;
    private MoviesApi mMoviesApi = null;
    private ProgressListener<BaseResponseBean> apiFetchListener;
    private boolean isMoviesAPILoading = false;

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ApiManager getInstance() {
        if (sApiManager == null) {
            sApiManager = new ApiManager();
        }
        return sApiManager;
    }

    @NonNull
    private MoviesApi getMoviesApi() {
        if (mMoviesApi != null) {
            mMoviesApi.clearListener();
        }
        mMoviesApi = new MoviesApi();
        return mMoviesApi;
    }

    /**
     * Fetch movies list.
     *
     * @param listener  the listener
     * @param sortOrder the sort order
     * @param pageCount the page count
     */
    public void fetchMoviesList(ProgressListener<BaseResponseBean> listener, String sortOrder, int pageCount) {

        apiFetchListener = listener;
        if (apiFetchListener != null) {
            apiFetchListener.inProgress();
        }
        if (isMoviesAPILoading) {
            return;
        }
        isMoviesAPILoading = true;

        getMoviesApi().fetchMoviesList(new BaseApi.BaseAPIListener() {
            @Override
            public void requestCompleted(BaseResponseBean response) {
                isMoviesAPILoading = false;
                // return data
                if (apiFetchListener != null) {
                    apiFetchListener.completed(response);
                    apiFetchListener = null;
                }
            }

            @Override
            public void requestFailed(RetrofitError error) {
                // return error
                if (apiFetchListener != null) {
                    apiFetchListener.failed(error.getMessage());
                    apiFetchListener = null;
                }
            }

        }, sortOrder, pageCount);
    }

    /**
     * The interface Progress listener.
     *
     * @param <T> the type parameter
     */
    public interface ProgressListener<T> {

        /**
         * In progress.
         */
        void inProgress();

        /**
         * Failed.
         *
         * @param message the message
         */
        void failed(String message);

        /**
         * Completed.
         *
         * @param object the object
         */
        void completed(T object);

    }
}
