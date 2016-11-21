<?php 


	print_r(get_http_raw());




/** 
* 获取HTTP请求原文 
* @return string 
*/ 
	function get_http_raw() { 
		$raw = ''; 

		// (1) 请求行 
		$raw .= $_SERVER['REQUEST_METHOD'].' '.$_SERVER['REQUEST_URI'].' '.$_SERVER['SERVER_PROTOCOL']."\r\n"; 

		// (2) 请求Headers 
		foreach($_SERVER as $key => $value) { 
			if(substr($key, 0, 5) == 'HTTP_') { 
				$key = substr($key, 5); 
				$key = str_replace('_', '-', $key); 
				$raw .= $key.': '.$value."\r\n"; 
			} 
		} 

		// (3) 空行 
		$raw .= "\r\n"; 

		// (4) 请求Body 
		$raw .= file_get_contents('php://input'); 





		return "{'code':0,'message':'成功','time':123121823,'data':[{'return':'".$raw."'}]}"; 
	}







?>