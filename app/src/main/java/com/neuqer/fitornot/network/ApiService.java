package com.neuqer.fitornot.network;


import com.neuqer.fitornot.business.account.model.request.GetVCodeRequestModel;
import com.neuqer.fitornot.business.account.model.request.RegisterByVCodeRequestModel;
import com.neuqer.fitornot.business.account.model.response.RegisterByVCodeResponseModel;
import com.neuqer.fitornot.business.circle.model.request.CommentRequestModel;
import com.neuqer.fitornot.business.circle.model.response.CommentsModel;
import com.neuqer.fitornot.business.circle.model.response.MomentsModel;
import com.neuqer.fitornot.business.clothes.modle.request.GetClothesByWordReqModle;
import com.neuqer.fitornot.business.clothes.modle.request.SetClothesReqModle;
import com.neuqer.fitornot.business.clothes.modle.request.UpdataClothesReqModle;
import com.neuqer.fitornot.business.clothes.modle.response.GetClothesRspModle;
import com.neuqer.fitornot.business.clothes.modle.response.GetSuitByWordRspModle;
import com.neuqer.fitornot.business.fittingroom.model.request.CreateMomentReqModle;
import com.neuqer.fitornot.business.fittingroom.model.request.SaveSuitReqModel;
import com.neuqer.fitornot.business.fittingroom.model.request.SetClothesModel;
import com.neuqer.fitornot.business.fittingroom.model.response.GetClothesRspModel;
import com.neuqer.fitornot.business.fittingroom.model.response.GetSuitRspModle;
import com.neuqer.fitornot.business.mine.model.request.SetUserInfoModel;
import com.neuqer.fitornot.business.mine.model.response.FollowedModel;
import com.neuqer.fitornot.business.mine.model.response.FollowingModel;
import com.neuqer.fitornot.business.mine.model.response.UserInfoModel;
import com.neuqer.fitornot.network.RequestBean.CommentReqModel;
import com.neuqer.fitornot.network.RequestBean.CreateMomentModel;
import com.neuqer.fitornot.network.RequestBean.SetNameModel;
import com.neuqer.fitornot.network.RequestBean.SetSuitModel;
import com.neuqer.fitornot.network.RequestBean.UpdateClothesModel;
import com.neuqer.fitornot.network.ResponseBean.CommentRspModel;
import com.neuqer.fitornot.network.ResponseBean.ConfigModel;
import com.neuqer.fitornot.network.ResponseBean.DetailedInfoModel;
import com.neuqer.fitornot.network.ResponseBean.FollowModel;
import com.neuqer.fitornot.network.ResponseBean.MomentModel;
import com.neuqer.fitornot.network.ResponseBean.SuitRspModel;
import com.neuqer.fitornot.network.response.ApiResponse;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import rx.internal.operators.OperatorOnBackpressureDrop;

public interface ApiService {
    //发送手机号码
    @POST("user/getVCode")
    Observable<ApiResponse<Void>> getVCode(@Body GetVCodeRequestModel model);

    //验证短信登陆
    @POST("user/registerByVCode")
    Observable<ApiResponse<RegisterByVCodeResponseModel>> rigistorByVCode(@Body RegisterByVCodeRequestModel mRegisterByVCodeModel);

    //设置用户信息 服务器出错
    @POST("user/userInfo")
    Observable<ApiResponse<Void>> setUserInfo(@Body SetUserInfoModel setUserInfoModel);

    //获得用户信息
    @GET("user/userInfo")
    Observable<ApiResponse<UserInfoModel>> getUserInfo();

    //设置服装，可多项一起添加
    @POST("clothes/clothes")
    Observable<ApiResponse<Void>> setClothes(@Body SetClothesModel clothesModels);

    //获得全部服装,四种范畴
    @GET("clothes/clothes")
    Observable<ApiResponse<GetClothesRspModel>> getClothes();


    //获取所有moment
    @GET("moment/moment")
    Observable<ApiResponse<MomentsModel>> getMoments(@Query("page") int pageID);

    //获取自己关注的allFollowingMoment
    @GET("moment/allFollowingMoment")
    Observable<ApiResponse<MomentsModel>> getAllFollowingMoments(@Query("page") int pageID);

    //获取自己关注的allFollowingMoment
    @GET("moment/allLikedMoment")
    Observable<ApiResponse<MomentsModel>> getAllLikedMoments(@Query("page") int pageID);

    //获取某人信息，包括userInfo和moment(通过openid查找）
    @GET("user/othersInfo/{openId}")
    Observable<ApiResponse<DetailedInfoModel>> getOthersInfo(@Path("openId") String openId);

    //通过openId获取某人的moment
    @GET("moment/getonesmoment/{openId}")
    Observable<ApiResponse<MomentsModel>> getOnesMoment(@Path("openId") int openId,@Query("page") int page);

    //通过openId添加关注（0：关注成功，-2："已经关注,不能重复关注"）
    @GET("user/follow/{openId}")
    Observable<ApiResponse<Void>> createFollow(@Path("openId") String openId);

    //检查是否关注（通过openId) -1代表是，0代表否
    @GET("user/checkIfFollowed/{openId}")
    Observable<ApiResponse<Void>> checkIfFollowed(@Path("openId") String openId);

    //取消关注（通过openId）
    @DELETE("user/follow/{openId}")
    Observable<ApiResponse<Void>> deleteFollow(@Path("openId") String openId);

    //获得所有的粉丝
    @GET("user/allFollowed")
    Observable<ApiResponse<FollowedModel>> getAllFollowed();

    //获得所有主动关注的人
    @GET("user/allFollowing")
    Observable<ApiResponse<FollowingModel>> getAllFollowing();

    //添加喜爱 （通过。。）
    @GET("moment/like/{id}")
    Observable<ApiResponse<Void>> createLike(@Path("id") int id);

    //删除喜爱
    @DELETE("moment/like/{id}")
    Observable<ApiResponse<Void>> deleteLike(@Path("id") int id);

    //查看是否喜爱 -1：是，0：否
    @GET("moment/checkIfLiked/{id}")
    Observable<ApiResponse<Void>> checkIfLiked(@Path("id") int id);

    //通过openId获取某人的nickname
    @GET("user/getNicknameByOpenid/{openId}")
    Observable<ApiResponse<String>> getNicknameByOpenid(@Path("openId") String openId);

    //创造评论到指定的页面(moment)
    @POST("moment/comment")
    Observable<ApiResponse<Void>> createComment(@Body CommentRequestModel commentReqModel);

    //删除评论 (通过评论的id）
    @DELETE("moment/comment/{id}")
    Observable<ApiResponse<Void>> deleteComment(@Path("id") int id);

    //获得某moment下的评论
    @GET("moment/comment/{momentId}")
    Observable<ApiResponse<List<CommentsModel>>> getCommentByMoment(@Path("momentId") int momentId);

    //获得config
    @GET("user/getConfig")
    Observable<ApiResponse<ConfigModel>> getConfig();


    //获得所有的服装
    @GET("clothes/suit")
    Observable<ApiResponse<List<SuitRspModel>>> getSuit();

    //穿衣服（根据suit的id)
    @GET("clothes/suit/wear/{suitId}")
    Observable<ApiResponse<Void>> wearSuit(@Path("suitId") int suitId);

    //设置config(不明id)
    @GET("user/setConfig/{id}")
    Observable<ApiResponse<Void>> setConfig(@Path("id") int id);

    //设置nickname和头像url
    @POST("user/setName")
    Observable<ApiResponse<Void>> setName(@Body SetNameModel setNameModel);

    //删除moment 未知id，叠加服务器有问题
    @DELETE("moment/moment/{id}")
    Observable<ApiResponse<Void>> deleteMoment();

    @POST("clothes/clothes")
    Observable<ApiResponse<Void>> setNewClothes(@Body SetClothesReqModle mModle);

    @GET("clothes/clothes")
    Observable<ApiResponse<GetClothesRspModle>> getAllClothes();

    @POST("clothes/clothes/like")
    Observable<ApiResponse<GetClothesRspModle>> getClothesByWord(@Body GetClothesByWordReqModle mModle);

    //设置搭配
    @POST("clothes/suit")
    Observable<ApiResponse<Void>> setNewSuit(@Body SaveSuitReqModel mSuitReqModel);

    //获取搭配
    @GET("clothes/suit")
    Observable<ApiResponse<GetSuitRspModle>> getNewSuit();

    //发布创建圈子
    @POST("moment/moment")
    Observable<ApiResponse<Void>> createMoment(@Body CreateMomentReqModle mCreateMomentReqModle);

    //通过单词获得搭配
    @POST("clothes/suit/like")
    Observable<ApiResponse<GetSuitByWordRspModle>> getSuitByWord(@Body GetClothesByWordReqModle mModle);

    @PUT("clothes/clothes")
    Observable<ApiResponse<Void>> updateClothes(@Body UpdataClothesReqModle mModle);

    @DELETE("clothes/clothes/{id}")
    Observable<ApiResponse<Void>> deleteClothes(@Path("id") int suitId);

    @DELETE("clothes/suit/{id}")
    Observable<ApiResponse<Void>> deleteSuit(@Path("id") int suitId);
}
