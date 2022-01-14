package com.builbee.app.util

import com.builbee.app.model.*
import com.builbee.app.model.ProjectSearchResults.ProjectSearchResultsResponse
import com.builbee.app.model.UserSearchResults.UserSearchResultsResponse
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

class RestApi {

    companion object {
        private var api: Api? = null

        fun getService(): Api? {
            if (api == null) {
                val logging = HttpLoggingInterceptor()
                val httpClient = OkHttpClient.Builder()
                httpClient.readTimeout(15, TimeUnit.SECONDS)
                httpClient.connectTimeout(15, TimeUnit.SECONDS)
                httpClient.addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BODY))
                val retrofit: Retrofit = Retrofit.Builder()
                    .baseUrl(Constants.k_SERVER_IP)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build()
                api = retrofit.create(Api::class.java)
            }
            return api
        }

        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            val httpClient = OkHttpClient.Builder()
            httpClient.readTimeout(15, TimeUnit.SECONDS)
            httpClient.connectTimeout(15, TimeUnit.SECONDS)
            httpClient.addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BODY))
            Retrofit.Builder()
                .baseUrl(Constants.k_PushNotificationApi)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
        }

        val notificationApi by lazy{
            retrofit.create(Api::class.java)
        }
    }

    interface Api {

        @FormUrlEncoded
        @POST("signup")
        fun signUp(
            @Field("name") name: String,
            @Field("email") email: String,
            @Field("password") password: String,
            @Field("location") location: String,
            @Field("phone_number") phoneNumber: String,
            @Field("longitude") longitude: String,
            @Field("latitude") latitude: String,
            @Field("user_type") userType: String,
            @Field("device_token") deviceToken: String?,
            @Field("company_name") companyName: String?,
            @Field("lisence_number") licenseNumber: String?,
            @Field("experience") experience: String?,
            @Field("website") website: String?,
            @Field("availability") availability: String?,
            @Field("social_id") socialId: String?,
            @Field("area_of_exp_id[]") areaOfExpIds: List<Int>?,
        ): Call<AuthResponse>?

        @FormUrlEncoded
        @POST("updateuser")
        fun updateProfile(
            @Header("Authorization") token: String?,
            @Field("name") name: String?,
            @Field("email") email: String?,
            @Field("location") location: String?,
            @Field("phone_number") phoneNumber: String,
            @Field("longitude") longitude: String?,
            @Field("latitude") latitude: String?,
            @Field("user_type") userType: String?,
            @Field("device_token") deviceToken: String?,
            @Field("company_name") companyName: String?,
            @Field("lisence_number") licenseNumber: String?,
            @Field("experience") experience: String?,
            @Field("availability_start") startDate: String?,
            @Field("availability_end") endDate: String?,
            @Field("area_of_exp_id[]") areaOfExpIds: List<Int>?,
            @Field("bio") bio: String?,
            @Field("linkedin") linkedin: String?,
            @Field("facebook") facebook: String?,
            @Field("instagram") instagram: String?,
            @Field("twitter") twitter: String?,
            @Field("profile_img_url") userImage: String?,
            @Field("website") website: String?,
        ): Call<AuthResponse>?

        @FormUrlEncoded
        @POST("login")
        fun login(
            @Field("email") email: String?,
            @Field("password") password: String?,
            @Field("device_token") deviceToken: String?
        ): Call<AuthResponse>?

        @FormUrlEncoded
        @POST("social/login")
        fun socialLogin(
            @Field("social_id") id: String?,
            @Field("device_token") deviceToken: String?
        ): Call<AuthResponse>?

        @GET("logout")
        fun logout(
            @Header("Authorization") token: String?
        ) : Call<GeneralResponse>?

        @GET("people")
        fun getNearByPeople(
            @Header("Authorization") token: String?,
            @Query("page") pageNumber: Int,
            @Query("latitude") latitude: Float,
            @Query("longitude") longitude: Float,
        ) : Call<GetNearByPeopleResponse>?

        @GET("project/id={id}")
        fun getIndividualProjects(
            @Header("Authorization") token: String?,
            @Path(value = "id", encoded = false) id: String?,
            @Query("page") pageNumber: Int,
        ) : Call<IndividualProjectResponse>?

        @GET("hitContractor")
        fun viewsIncrement(
            @Query("user_id") userId: String?,
            @Query("project_id") projectId: Int?
        ) : Call<GeneralResponse>?

        @GET("resetpassword")
        fun forgotPassword(
            @Query("email") email: String?,
            @Query("is_code_send") codeSend: Boolean?,
            @Query("code") code: String?,
            @Query("new_password") newPassword: String?
        ) : Call<ForgotPasswordResponse>?

        @GET("getAreaXp")
        fun getUserAreaOfExperience(
            @Header("Authorization") token: String?
        ) : Call<AreaOfExperienceResponse>?

        @GET("all_area_of_experience")
        fun getAllAreaOfExperience(
            @Header("Authorization") token: String?
        ) : Call<AreaOfExperienceResponse>?

        @POST("all_area_of_experience")
        fun getAreaOfExperience() : Call<AreaOfExperienceResponse>?

        @POST("project")
        fun addProject(
            @Header("Authorization") token: String?,
            @Body body: RequestBody
        ): Call<GeneralResponse>?

        @POST("updateproject")
        fun editProject(
            @Header("Authorization") token: String?,
            @Body body: RequestBody
        ): Call<GeneralResponse>?

        @GET("project")
        fun getProjectList(
            @Header("Authorization") token: String?,
            @Query("page") pageNumber: Int
        ): Call<IndividualProjectResponse>?

        @GET("all_post")
        fun getPosts(
            @Header("Authorization") token: String?,
            @Query("page") pageNumber: Int
        ): Call<GetPostResponse>?

        @GET("post")
        fun getMyPosts(
            @Header("Authorization") token: String?,
            @Query("page") pageNumber: Int
        ): Call<GetPostResponse>?

        @POST("post")
        fun addPost(
            @Header("Authorization") token: String?,
            @Body body: RequestBody
        ): Call<GeneralResponse>?

        @POST("updatePost")
        fun editPost(
            @Header("Authorization") token: String?,
            @Body body: RequestBody
        ): Call<GeneralResponse>?

        @DELETE("post")
        fun deletePost(
            @Header("Authorization") token: String?,
            @Query("post_id") postId: Int
        ): Call<GeneralResponse>?

        @GET("hitpost")
        fun viewsIncrementPost(
            @Query("user_id") userId: Int?,
            @Query("post_id") postId: Int?
        ) : Call<GeneralResponse>?

        @DELETE("project")
        fun deleteProject(
            @Header("Authorization") token: String?,
            @Query("project_id") postId: Int
        ): Call<GeneralResponse>?

        @GET("report_types")
        fun getAllReportType(
            @Header("Authorization") token: String?
        ) : Call<ReportTypeResponse>?

        @GET("reportpost")
        fun reportPost(
            @Header("Authorization") token: String?,
            @Query("reported_post") reportedPostId: Int,
            @Query("reason") body: String?,
            @Query("report_type") reportType: String?
        ): Call<GeneralResponse>?

        @GET("reportproject")
        fun reportProject(
            @Header("Authorization") token: String?,
            @Query("reported_project") reportedProjectId: Int,
            @Query("reason") body: String?,
            @Query("report_type") reportType: String?
        ): Call<GeneralResponse>?

        @GET("reportuser")
        fun reportUser(
            @Header("Authorization") token: String?,
            @Query("reported_person") reportedPersonId: Int,
            @Query("reason") body: String?,
            @Query("report_type") reportType: String?
        ): Call<GeneralResponse>?

        @GET("search")
        fun search(
            @Header("Authorization") token: String,
            @Query("page") pageNo: Int,
            @Query("general_search") query: String?,
            @Query("name") name: String?,
            @Query("phone_number") phone: String?,
            @Query("bio") bio: String?
        ): Call<SearchResponse>?

//        @Headers("Authorization: key=${Constants.k_ServerKey}, Content-type:${Constants.k_ContentType}")
        @Headers("Authorization:key=AAAA-lD3i6s:APA91bHBnRQQmwVJ8ZEYqo9S9sCzFuaLMp7eCLj3rZ7DZFy8bRxCbyaK6nyBPMVzh4cnx42HtTXLYsuSygDBYn6TLuhvJbF2XfR26OiGtyTFFBJjXRKlHI7i-g7s2E5Rcl-RMPnPRH-i, Content-type:application/json")
        @POST("fcm/send")
        suspend fun pushNotification(
            @Body notification: PushNotification
        ): Response<ResponseBody>


        @POST("searchApi")
        fun getSearchResults(
            @Header("Authorization") token: String?,
            @Query("q") q: String,
            @Query("source") source: String,
            @Query("page") page: Int
        ): Call<UserSearchResultsResponse>?

        @POST("searchApi")
        fun getSearchResults(
            @Header("Authorization") token: String?,
            @Query("q") q: String,
            @Query("source") source: String,
            @Query("page") page: Int,
            @Query("user_id") user_id: String
        ): Call<ProjectSearchResultsResponse>?

    }

}