</div>
</div>
<br />
<br />
<div id="footer">
    <div class="container">
        <p class="text-muted">
            Burléon Junior<br /> <a href="mailto:email@hotmail.com">Contact
                the webmaster</a>
        </p>
    </div>
</div>

<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/dashboard.js"></script>
<script src="js/sorttable.js"></script>
<script src="js/bootstrap-notify.min.js"></script>    

<!-- https://www.kryogenix.org/code/browser/sorttable -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/1000hz-bootstrap-validator/0.11.9/validator.js"></script>

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

</body>
</html>
