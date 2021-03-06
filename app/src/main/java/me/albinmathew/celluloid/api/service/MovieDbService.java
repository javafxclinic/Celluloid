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

package me.albinmathew.celluloid.api.service;

import me.albinmathew.celluloid.api.base.BaseMovieBean;
import me.albinmathew.celluloid.api.base.BaseReviewBean;
import me.albinmathew.celluloid.api.base.BaseVideoBean;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * The interface Movie db service.
 *
 * @author albin
 * @date 1 /2/16
 */
public interface MovieDbService {

    /**
     * Gets movies list.
     *
     * @param sort_type    the sort type
     * @param api_key      the api key
     * @param page         the page
     * @param includeAdult the include adult
     * @param callback     the callback
     */
    @GET("/3/discover/movie")
    void getMoviesList(@Query("sort_by") String sort_type,
                       @Query("api_key") String api_key, @Query("page") int page, @Query("include_adult") boolean includeAdult,
                       Callback<BaseMovieBean> callback);

    /**
     * Gets genre list.
     *
     * @param api_key the api key
     */
    @GET("/genre/movie/list")
    void getGenreList(@Query("api_key") String api_key);

    /**
     * Gets videos list.
     *
     * @param movieId  the movie id
     * @param api_key  the api key
     * @param callback the callback
     */
    @GET("/3/movie/{id}/videos")
    void getVideosList(@Path("id") long movieId, @Query("api_key") String api_key, Callback<BaseVideoBean> callback);

    /**
     * Gets reviews list.
     *
     * @param movieId  the movie id
     * @param api_key  the api key
     * @param page     the page
     * @param callback the callback
     */
    @GET("/3/movie/{id}/reviews")
    void getReviewsList(@Path("id") long movieId, @Query("api_key") String api_key, @Query("page") int page,
                        Callback<BaseReviewBean> callback);
}
