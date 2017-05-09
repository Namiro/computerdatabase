
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
        var value = document.getElementById("popup").value ;
        console.log(value);
        if(value === "popupsignup") {
            $('#myModalSignup').modal('show');
         }
        if(value === "popuplogin") {
            $('#myModalLogin').modal('show');
         }
    });
</script>

</body>
</html>
