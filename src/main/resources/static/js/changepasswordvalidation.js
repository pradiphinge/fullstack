$(document).ready(function() {

		$("#password").change(function() {

			$.ajax({

				url : 'validatePassword',
				data : {
					password : $("#password").val()
				},
				success : function(responseText) {

					$("#errMsg").text(responseText);
					if (responseText != "") {
						$("#password").focus();
						$("#btnSubmit").attr("disabled",true);
					}else{
						$("#btnSubmit").attr("disabled",false);
					}

				}
			});
		});
	
		
		
	});