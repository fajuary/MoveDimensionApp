package dongwei.fajuary.movedimensionapp.httpModel;

/**
 * Created by Administrator on 2017/5/11.
 */

public class HttpUtils {
    public static String TAG="debug-okhttp";
    public static boolean isDebug=true;
    //线上接口
    public static String baseUrl="http://211.149.207.217:8083/";
    //阿红接口
//    public static String baseUrl="http://192.168.1.105:8081/";
    //登录
    public static String loginUrl=baseUrl+"user/loginAndRegister.do";
    //用户基础信息
    public static String userBasicsInfoUrl=baseUrl+"user/userBasicsInformation.do";
    //根据类型查询规则
    public static String getRuleByTypeUrl=baseUrl+"user/getRuleByType.do";
    //规则
    public static String userRuleUrl=baseUrl+"user/userRule.do";

    //用户视频
    public static String getUserYearVideoListUrl=baseUrl+"video/worshipYearVideo.do";
    //企业视频
    public static String getEnterpriseVideoListUrl=baseUrl+"video/enterpriseVideo.do";
    //
    public static String getMyVideosListUrl=baseUrl+"video/myVideos.do";

    //查询排行榜
    public static String getRankingVideoListUrl=baseUrl+"ranking/getRanking.do";
    //特效列表
    public static String getSpecialEffectsUrl=baseUrl+"specialEffects/specialEffects.do";
    //购买特效
    public static String getSpecialEffectOrderUrl=baseUrl+"specialEffects/specialEffectOrder.do";

    //阿里视频上传失败处理
    public static String getUploadVideoUrl=baseUrl+"video/uploadVideoFail.do";
    //获取阿里云临时的凭证
    public static String getAssumeRoleUrl=baseUrl+"video/getAssumeRole.do";

    //生成视频
    public static String getAddVideoUrl=baseUrl+"video/addVideo.do";
    //更新视频
    public static String updateVideoUrl=baseUrl+"video/updateVideo.do";

    //意见反馈新增
    public static String getAddFeedBackUrl=baseUrl+"user/addFeedBack.do";
    //用户个人资料
    public static String getPersonalDataUrl=baseUrl+"user/personalData.do";
    //发送短信验证码
    public static String sendMsgCodeUrl=baseUrl+"sms/send.do";
    //手机号登录
    public static String phoneLoginUrl=baseUrl+"user/phoneLogin.do";


    //绑定手机号
    public static String bindingPhoneUrl=baseUrl+"user/bindingPhone.do";
    //安卓版本更新
    public static String getVersionUpdateUrl=baseUrl+"user/findversion.do";



}
