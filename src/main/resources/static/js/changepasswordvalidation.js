$(document).ready(function() {

		$("#password").change(function() {
			$("#confirmPassword").val("");
			$("#btnSubmit").attr("disabled",true);
			$.ajax({

				url : 'validatePassword',
				data : {
					password : $("#password").val(),
					confirmPassword :$("#confirmPassword").val()
				},
				success : function(responseText) {

					$("#errMsg").text(responseText);
					if (responseText != "") {
						$("#password").val("");
						$("#password").focus();
						$("#btnSubmit").attr("disabled",true);
					}

				}
			});
		});
	
		$("#confirmPassword").change(function() {
			$("#btnSubmit").attr("disabled",true);
			$.ajax({

				url : 'validateConfirmPassword',
				data : {
					confirmPassword : $("#confirmPassword").val(),
					password :$("#password").val()
				},
				success : function(responseText) {

					$("#errMsg2").text(responseText);
					if (responseText != "") {
						$("#confirmPassword").val("");
						$("#confirmPassword").focus();
						$("#btnSubmit").attr("disabled",true);
					}else{
						$("#btnSubmit").attr("disabled",false);
					}

				}
			});
		});
	
		
	});