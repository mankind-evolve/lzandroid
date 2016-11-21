//
//import org.apache.http.HttpHost;
//
///**
// * @ClassName: ProxySettings
// * @Description: gprs、wlan http proxy
// * @author
// * @date
// *
// */
//public class LZHttpProxySettings {
//	/**
//	* @Title: getProxyHost
//	* @Description: 获取用户设置的http proxy
//	* @return HttpHost
//	*/
//	public static HttpHost getProxyHost() {
//		String host = android.net.Proxy.getDefaultHost();
//		int port = android.net.Proxy.getDefaultPort();
//		if (host != null && port != -1) {
//			return new HttpHost(host, port);
//		} else {
//			return null;
//		}
//	}
//}
