<?php 


	print_r(get_http_raw());




/** 
* 获取HTTP请求原文 
* @return string 
*/ 
	function get_http_raw() { 
		$raw = ''; 


		$page = 1 ;
		if ($_GET['page'] != 0) {
			$page = $_GET['page'];
		}
		



		$data = "";

		for ($i=0; $i <10 ; $i++) { 

			if ($i==9) {
				$data .= "{'nickname':'第".$page."页 老王 编号 ".$i."','headimg':'http://app.zhuli6.com/lzAndroid/headimg/head".$i.".jpg'}";
			}else{
				$data .= "{'nickname':'第".$page."页 老王 编号 ".$i."','headimg':'http://app.zhuli6.com/lzAndroid/headimg/head".$i.".jpg'},";
			}
			
		}

		return "{'code':0,'message':'成功','time':123121823,'data':[".$data."]}"; 
	}







?>