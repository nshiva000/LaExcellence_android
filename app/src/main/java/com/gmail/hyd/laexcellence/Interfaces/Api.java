package com.gmail.hyd.laexcellence.Interfaces;

import com.gmail.hyd.laexcellence.Models.AttendaceModel.AttendaceModel;
import com.gmail.hyd.laexcellence.Models.Coupon.Coupon;
import com.gmail.hyd.laexcellence.Models.CreateNewTestModel.CreateNewTestModel;
import com.gmail.hyd.laexcellence.Models.DeviceToken.DeviceToken;
import com.gmail.hyd.laexcellence.Models.DownloadsModel.DownloadsModel;
import com.gmail.hyd.laexcellence.Models.Edit.Edit_response;
import com.gmail.hyd.laexcellence.Models.ExamList.ExamList;
import com.gmail.hyd.laexcellence.Models.ForgotPassword.ForgotPassword;
import com.gmail.hyd.laexcellence.Models.HandoutsModel.HandoutsModel;
import com.gmail.hyd.laexcellence.Models.Login.Login;
import com.gmail.hyd.laexcellence.Models.NoticeBoardModel.NoticeBoardModel;
import com.gmail.hyd.laexcellence.Models.Notification.Notification;
import com.gmail.hyd.laexcellence.Models.PayNow.PayNow;
import com.gmail.hyd.laexcellence.Models.Product.Product;
import com.gmail.hyd.laexcellence.Models.QuestionBankWriteTestModel.QuestionBankWriteTestModel;
import com.gmail.hyd.laexcellence.Models.QuestionsList.QuestionsList;
import com.gmail.hyd.laexcellence.Models.RaiseDoubt.RaiseDoubt;
import com.gmail.hyd.laexcellence.Models.ReplyDoubt.ReplyDoubt;
import com.gmail.hyd.laexcellence.Models.ReportQuestion.ReportQuestion;
import com.gmail.hyd.laexcellence.Models.SchedulesModel.SchedulesModel;
import com.gmail.hyd.laexcellence.Models.Signup.Signup;
import com.gmail.hyd.laexcellence.Models.Submit.Submit;
import com.gmail.hyd.laexcellence.Models.SubmitQuestionBankTest.SubmitQuestionBankTest;
import com.gmail.hyd.laexcellence.Models.TestAnalysisModel.TestAnalysisModel;
import com.gmail.hyd.laexcellence.Models.TestForms.TestForms;
import com.gmail.hyd.laexcellence.Models.TestListModel.TestListModel;
import com.gmail.hyd.laexcellence.Models.TestResultsModel.TestResultsModel;
import com.gmail.hyd.laexcellence.Models.TopicsListModel.TopicsListModel;
import com.gmail.hyd.laexcellence.Models.UserAllDoubts.UserAllDoubts;
import com.gmail.hyd.laexcellence.Models.UserSingleDoubts.UserSingleDoubts;
import com.gmail.hyd.laexcellence.Models.Version.Version;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface Api {

    @FormUrlEncoded
    @POST("studentlogin")
    Call<Login> user_login(
            @Field("username") String username,
            @Field("password") String password
    );


    @FormUrlEncoded
    @POST("prelims")
    Call<ExamList> exam_list(
            @Field("sid") String sid
    );


    @GET("prelims/{qid}")
    Call<List<QuestionsList>> get_all_questions(@Path("qid") String qid);


    @FormUrlEncoded
    @POST("submittest")
    Call<Submit> submit_answers(
            @Field("sid") String sid,
            @Field("tid") String tid,
            @Field("answers") String ans
    );

    @FormUrlEncoded
    @POST("editprofile")
    Call<Edit_response> edit_profile(
            @Field("sid") String sid,
            @Field("email") String email
    );


    @GET("downloads/2")
    Call<DownloadsModel> getDownloads();


    @GET("downloads/1")
    Call<HandoutsModel> getHandouts();


    @GET("schedules/{studentid}")
    Call<SchedulesModel> get_schedule(@Path("studentid") String sid);


    @GET("noticeboard/{studentid}")
    Call<NoticeBoardModel> get_noticeBoard(@Path("studentid") String sid);

    @GET("attendance/{studentid}")
    Call<AttendaceModel> get_attendance(@Path("studentid") String sid);


    @GET("version")
    Call<Version> check_version();


    @FormUrlEncoded
    @POST("instamojosold")
    Call<PayNow> pay_money(
            @Field("sid") String sid,
            @Field("order_id") String order_id,
            @Field("payment_id") String payment_id,
            @Field("coupon") String coupon
    );

    @FormUrlEncoded
    @POST("signup")
    Call<Signup> user_register(
            @Field("name") String name,
            @Field("email") String email,
            @Field("mobile") String mobile,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("products")
    Call<Product> get_product(
            @Field("sid") String sid
    );

    @FormUrlEncoded
    @POST("coupon")
    Call<Coupon> apply_coupon(
            @Field("sid") String sid,
            @Field("pid") String pid,
            @Field("coupon") String coupon
    );


    @FormUrlEncoded
    @POST("forgotpassword")
    Call<ForgotPassword> forgot_password(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("devicetoken")
    Call<DeviceToken> send_deviceToken(
            @Field("sid") String sid,
            @Field("token") String token
    );


    @FormUrlEncoded
    @POST("notifications")
    Call<Notification> get_notifications(
            @Field("sid") String sid
    );

    @FormUrlEncoded
    @POST("reportquestion")
    Call<ReportQuestion> send_report_to_server(
            @Field("sid") String sid,
            @Field("qid") String qid,
            @Field("issue") String issue
    );

    @FormUrlEncoded
    @POST("raisedoubt")
    Call<RaiseDoubt> raise_doubt(
            @Field("sid") String sid,
            @Field("qid") String qid,
            @Field("issue") String issue
    );


    @FormUrlEncoded
    @POST("userdoubts")
    Call<UserAllDoubts> user_all_doubts(
            @Field("sid") String sid
    );

    @FormUrlEncoded
    @POST("userdoubt")
    Call<UserSingleDoubts> user_single_doubts(
            @Field("did") String did
    );


    @FormUrlEncoded
    @POST("replytodoubt")
    Call<ReplyDoubt> reply_to_doubt(
            @Field("did") String did,
            @Field("sid") String sid,
            @Field("reply") String reply
    );

    @FormUrlEncoded
    @POST("myqbtests")
    Call<TestListModel> get_all_tests(
            @Field("sid") String sid
    );

    @FormUrlEncoded
    @POST("writeqbanktest")
    Call<QuestionBankWriteTestModel> get_question_bank_test(
            @Field("sid") String sid,
            @Field("tid") String tid
    );

    @FormUrlEncoded
    @POST("resultsqbanktest")
    Call<TestResultsModel> get_question_results(
            @Field("sid") String sid,
            @Field("tid") String tid
    );


    @POST("characteristics/form")
    Call<TestForms> get_test_forms();

    @FormUrlEncoded
    @POST("studentqbank")
    Call<TopicsListModel> get_subject_topic(
            @Field("sid") String sid
    );


    @FormUrlEncoded
    @POST("createQBankTest")
    Call<CreateNewTestModel> create_new_test(
            @Field("sid") String sid,
            @Field("topics") String topics,
            @Field("no_q") String no_q,
            @Field("q_forms") String q_forms,
            @Field("tutor_mode") String tutor_mode,
            @Field("timed_mode") String timed_mode
    );

    @FormUrlEncoded
    @POST("submitQBankTest")
    Call<SubmitQuestionBankTest> submit_question_bank_test(
            @Field("sid") String sid,
            @Field("tid") String tid,
            @Field("answers") String answers
    );

    @FormUrlEncoded
    @POST("analysisQBankTest")
    Call<TestAnalysisModel> get_test_analysis(
            @Field("sid") String sid,
            @Field("tid") String tid
    );



   /*

   studentQBank
   replytodoubt
   /instamojosold/{student id}
   @GET("exam_process.php")
    Call<List<TestList>> getExamList();
    @GET("exam_list.php")
    Call<List<Question>> getQuetions(@Query("exam_id") String id);
    */
}
