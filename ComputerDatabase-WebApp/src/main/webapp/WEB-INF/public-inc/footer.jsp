</div>
</div>
<br />
<br />

<div id="footer">
	<div class="container">
		<p class="text-muted">
			<div id="powered">
			<spring:message code="made_with_smile"/>
			</div>
			<div id="copyright">
				Copyright &copy; <script type="text/javascript"> document.write((new Date()).getFullYear()); </script> <spring:message code="all_rights_reserved"/>
			</div>
		</p>		
	</div>
</div>

<div class="container">
	<hr>
	<div class="text-center center-block">
		<a href="https://www.facebook.com/GroupeExcilys/"><i id="social-fb" class="fa fa-facebook-square fa-3x social"></i></a>
		<a href="https://twitter.com/excilys"><i id="social-tw"	class="fa fa-twitter-square fa-3x social"></i></a> 
		<a href="https://foursquare.com/v/excilys/4fd8432de4b000122d68e4c1"><i id="social-4sq" class="fa fa-foursquare fa-3x social"></i></a>
		<a href="https://plus.google.com/112772708169169527496"><i id="social-gp" class="fa fa-google-plus-square fa-3x social"></i></a>
		<a href="mailto:croissants@excilys.com"><i id="social-em" class="fa fa-envelope-square fa-3x social"></i></a>
	</div>
	<hr>
</div>

</body>

<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/dashboard.js"></script>
<script src="js/sorttable.js"></script>
<script src="js/bootstrap-notify.min.js"></script>    


<!-- https://www.kryogenix.org/code/browser/sorttable -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/1000hz-bootstrap-validator/0.11.9/validator.js"></script>

<script src="//cdn.jsdelivr.net/webshim/1.14.5/polyfiller.js"></script>

<script>
	webshim.activeLang('en-AU');
	webshims.setOptions('forms-ext', {
		types : 'date'
	});
	webshims.polyfill('forms forms-ext');
</script>

<script type="text/javascript">
    $(document).ready(function($) {
        var value = document.getElementById("popup").value;
        console.log(value);
        if(value === "popupsignup") {
            $('#myModalSignup').modal('show');
         }
        if(value === "popuplogin") {
            $('#myModalLogin').modal('show');
         }
        if(getParameterByName("notificationSuccessMessage") != null || document.getElementById("notificationSuccessMessage").value != "")
        {
            $.notify({
                title: '<strong>Success !</strong>',
                message: getParameterByName("notificationSuccessMessage") ? getParameterByName("notificationSuccessMessage") : document.getElementById("notificationSuccessMessage").value,
                icon: 'glyphicon glyphicon-ok-sign'
            },{
                newest_on_top: true,
                mouse_over:true,
                allow_dismiss: true,
                spacing: 20,
                offset:55,
                type: 'success'
            });
        }
        if(getParameterByName("notificationErrorMessage") != null || document.getElementById("notificationErrorMessage").value != "")
        {
            $.notify({
                title: '<strong>Error !</strong>',
                message: getParameterByName("notificationErrorMessage") ? getParameterByName("notificationErrorMessage") : document.getElementById("notificationErrorMessage").value,
                icon: 'glyphicon glyphicon-remove-sign'
            },{
                newest_on_top: true,
                mouse_over:true,
                allow_dismiss: true,
                spacing: 20,
                offset:55,
                type: 'danger'
            });
        }
        if(getParameterByName("notificationWarningMessage") != null || document.getElementById("notificationWarningMessage").value != "")
        {
            $.notify({
                title: '<strong>Be carful !</strong>',
                message: getParameterByName("notificationWarningMessage") ? getParameterByName("notificationWarningMessage") : document.getElementById("notificationWarningMessage").value,
                icon: 'glyphicon glyphicon-warning-sign'
            },{
                newest_on_top: true,
                mouse_over:true,
                allow_dismiss: true,
                spacing: 20,
                offset:55,
                type: 'warning'
            });
        }
        if(getParameterByName("notificationInfoMessage") != null || document.getElementById("notificationInfoMessage").value != "")
        {
            $.notify({
                title: '<strong>Infos !</strong>',
                message: getParameterByName("notificationInfoMessage") ? getParameterByName("notificationInfoMessage") : document.getElementById("notificationInfoMessage").value,
                icon: 'glyphicon glyphicon-info-sign'
            },{
                newest_on_top: true,
                mouse_over:true,
                allow_dismiss: true,
                spacing: 20,
                offset:55,
                type: 'info'
            });
        }
    });
    
    
    function getParameterByName(name, url) {
        if (!url) url = window.location.href;
        name = name.replace(/[\[\]]/g, "\\$&");
        var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
            results = regex.exec(url);
        if (!results) return null;
        if (!results[2]) return '';
        return decodeURIComponent(results[2].replace(/\+/g, " "));
    }
    
</script>

</html>
