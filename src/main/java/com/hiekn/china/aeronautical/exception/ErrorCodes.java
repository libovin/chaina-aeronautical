package com.hiekn.china.aeronautical.exception;

import com.hiekn.boot.autoconfigure.base.exception.ExceptionKeys;

public class ErrorCodes implements ExceptionKeys {
    //3xxxx:通用错误码定义
    //5xxxx:业务相关错误码定义
    //7xxxx:未知错误码
    //8xxxx:Http相关错误码定义
    //9xxxx:统一错误码及第三方服务错误码定义

	public final static int SYS_ERROR=51001;
	public final static int  TOKEN_GEN_ERROR=51002;
	public final static int TOKEN_DECODE_ERROR = 50003 ;
	public final static int TOKEN_EXPIRE_ERROR=50004;
	public final static int USER_IN_AUDIT=51005;
	public final static int USER_AUTH_NOT_PASS=51006;

	// 微信
	public final static int WX_API_USER_LOGIN_ERROR=51007;
	public final static int WX_API_GET_AUTHTOKEN_BYCODE_ERROR=51008;
	public final static int WX_API_GET_USERINFO_ERROR=51009;
	public final static int KG_API_INIT_ERROR=51010;

	public final static int PARAM_CHECKOUT_ERROR=51011;
	public final static int ATTTI_TOO_MORE=51012;
	public final static int ATTTI_IS_NULL=51013;
	public final static int DATANAME_TOO_LANG=51014;
	public final static int DATANAME_IS_NULL=51015;
	public final static int ATTNAME_IS_ERROR=51016;
	public final static int PARAM_NULL_ERROR=51017;
	public final static int PARAM_LENGTH_ERROR=51018;


	public final static int EXISTED_ERROR=51019;
	public final static int USER_NOT_FOUND_ERROR=51020;
	public final static int PWD_ERROR=51021;
	public final static int GET_CODE_ERROR=51022;
	public final static int VERIFY_MOBILE_CODE_ERROR=51023;
	public final static int VERIFY_CODE_ERROR=51024;
	public final static int USER_EXIST_ERROR=51025;
	public final static int UN_LOGIN_ERROR=51026;
	public final static int USER_PWD_ERROR=51027;
	public final static int PROJECT_CONFIG_ERROR=51028;
	public final static int USER_NO_PERMISSION=51029;
	public final static int AUTHENTICATION_ERROR=51030;
	public final static int TOKEN_CREATE_ERROR=51031;
	public final static int ID_NOTEXIST_ERROR=51032;
	public final static int NAME_EXIST_ERROR=51033;
	public final static int MYDATA_NOTEXIST_ERROR=51034;
	public final static int UPLAD_FILE_ERROR=51035;
	public final static int ADD_LIMIT_ERROR=51036;
	public final static int UN_CONNECT_GRAPH_ERROR=51037;
	public final static int ES_TYPE_ERROR=51038;
	public final static int ES_TYPE_CREATE_ERROR=51039;
	public final static int FILE_OUT_LIMIT=51040;
	public final static int FILE_NULL_ERROR=51041;
	public final static int FIELD_DEFINE_ERROR=51042;
	public final static int FILE_UPLOAD_ERROR=51043;
	public final static int FIELD_NULL_ERROR=51044;
	public final static int KG_NULL_ERROR=51045;
	public final static int FIELD_IMPORT_ERROR=51046;
	public final static int FIELD_DEFINE_ERROR_Second=51047;
	public final static int APK_NULL_ERROR=51048;
	public final static int DATA_KEY_NULL_ERROR=51049;
	public final static int DATA_NULL_ERROR=51050;
	public final static int FILE_NULL_EXIST=51051;
	public final static int GRAPH_FLAG_EXIST=51052;
	public final static int DATA_FLAG_EXIST=51053;
	public final static int GRAPH_KEY_NULL_ERROR=51054;
	public final static int EXCEL_ROW_NULL_ERROR=51055;
	public final static int EXCEL_CONTENT_NULL_ERROR=51056;
	public final static int GRAPH_KEY_NULL_VALUE=51057;
	public final static int GRAPH_TITLE_NULL_ERROR=51058;
	public final static int DATA_TITLE_NULL_ERROR=51059;
	public final static int INTERFACE_IMPORT_NULL_ERROR=51060;
	public final static int INTERFACE_IMPORT_NULL_CONTENT=51061;
	public final static int RETURN_PARAM_TYPE_ERROR=51062;
	public final static int INTERFACE_IMPORT_ERROR=51063;
	public final static int FILE_NAME_ERROR=51064;
	public final static int SCHEDULE_NULL_EXIST=51065;
	public final static int DEFAULT_GRAPH_EXIST = 51066;
	public final static int DATA_FORMAT_ERROR=51067;
	public final static int MYSQL_CONNECTION_ERROR=51068;

	public final static int STRING_ARRAY_FORMAT_ERROR=51069;
	public final static int STRING_ARRAY_FORMAT_ERROR2=51070;
	public final static int OBJECT_ARRAY_FORMAT_ERROR2=51071;
	public final static int CONNECT_ERROR=51072;
	public final static int VERSION_CREATE_ERROR = 51073;
	public final static int VERSION_DELETE_ERROR = 51074;
	public final static int VERSION_CHANGE_ERROR = 51075;
	public final static int TRY_ORDER_AGAIN = 51076;
	public final static int DOUBLE_FORMAT_ERROR = 51077;

	public final static int	CHECK_USER_INFO_ERROR = 51078;

	public final static int INTEGER_FORMAT_ERROR = 51079;

	public final static int CAPTCHA_NOT_RIGHT_ERROR=51080;

	public final static int GRAPH_TITLE_LENGTH_ERROR=51081;

	public final static int XSS_NULL = 51082;

}
