//On load
$(function() {
    // Default: hide edit mode
    $(".editMode").hide();
    
    // Click on "selectall" box
    $("#selectall").click(function () {
        $('.cb').prop('checked', this.checked);
    });

    // Click on a checkbox
    $(".cb").click(function() {
        if ($(".cb").length == $(".cb:checked").length) {
            $("#selectall").prop("checked", true);
        } else {
            $("#selectall").prop("checked", false);
        }
        if ($(".cb:checked").length != 0) {
            $("#deleteSelected").enable();
        } else {
            $("#deleteSelected").disable();
        }
    });

});

// Function setCheckboxValues
(function ( $ ) {

    $.fn.setCheckboxValues = function(formFieldName, checkboxFieldName) {

        var str = $('.' + checkboxFieldName + ':checked').map(function() {
            return this.value;
        }).get().join();
        
        $(this).attr('value',str);
        
        return this;
    };

}( jQuery ));

// Function toggleEditMode
(function ( $ ) {

    $.fn.toggleEditMode = function() {
        if($(".editMode").is(":visible")) {
            $(".editMode").hide();
            $("#editComputer").text("Edit");
        }
        else {
            $(".editMode").show();
            $("#editComputer").text("View");
        }
        return this;
    };

}( jQuery ));


// Function delete selected: Asks for confirmation to delete selected computers, then submits it to the deleteForm
(function ( $ ) {
    $.fn.deleteSelected = function() {
        if (confirm("Are you sure you want to delete the selected computers?")) { 
            $('#deleteForm input[name=submitDelete]').setCheckboxValues('submitDelete','cb');
            $('#deleteForm').submit();
        }
    };
}( jQuery ));



//Event handling
//Onkeydown
$(document).keydown(function(e) {

	switch (e.keyCode) {
        //DEL key
        case 46:
            if($(".editMode").is(":visible") && $(".cb:checked").length != 0) {
                $.fn.deleteSelected();
            }   
            break;
        //E key (CTRL+E will switch to edit mode)
        case 69:
            if(e.ctrlKey) {
                $.fn.toggleEditMode();
            }
            break;
    }
});

$(document).ready(function() {
    $(".dropdown img.flag").addClass("flagvisibility");

    $(".dropdown dt a").click(function() {
        $(".dropdown dd ul").toggle();
    });
                
    $(".dropdown dd ul li a").click(function() {
        var text = $(this).html();
        $(".dropdown dt a span").html(text);
        $(".dropdown dd ul").hide();
        $("#result").html("Selected value is: " + getSelectedValue("sample"));
    });
                
    function getSelectedValue(id) {
        return $("#" + id).find("dt a span.value").html();
    }

    $(document).bind('click', function(e) {
        var $clicked = $(e.target);
        if (! $clicked.parents().hasClass("dropdown"))
            $(".dropdown dd ul").hide();
    });

    $("#flagSwitcher").click(function() {
        $(".dropdown img.flag").toggleClass("flagvisibility");
    });
});

$(document).ready(function(){
	var fraImgLink = "img/French.gif";
	var engImgLink = "img/English.gif";
	var romImgLink = "img/Romania.png";

	var imgBtnSel = $('#imgBtnSel');
	var imgBtnFra = $('#imgBtnFra');
	var imgBtnEng = $('#imgBtnEng');
	var imgBtnRom = $('#imgBtnRom');

	var imgNavSel = $('#imgNavSel');
	var imgNavFra = $('#imgNavFra');
	var imgNavEng = $('#imgNavEng');
	var imgNavRom = $('#imgNavRom');

	var spanNavSel = $('#lanNavSel');
	var spanBtnSel = $('#lanBtnSel');

	imgBtnFra.attr("src", fraImgLink);
	imgBtnEng.attr("src", engImgLink);
	imgBtnRom.attr("src", romImgLink);

	document.getElementById("navFra").onclick = function() {
	    localStorage.clear();
	    localStorage.setItem('navFra', document.getElementById("navFra").value);
	}

	document.getElementById("navEng").onclick = function() {
	    localStorage.clear();
	    localStorage.setItem('navEng', document.getElementById("navEng").value);
	}
	
	document.getElementById("navRom").onclick = function() {
	    localStorage.clear();
	    localStorage.setItem('navRom', document.getElementById("navRom").value);
	}
	
	if (localStorage.getItem('navFra')) {
		imgNavSel.attr("src", fraImgLink);
		spanNavSel.text("FR");
	} else if (localStorage.getItem('navEng')) {
		imgNavSel.attr("src", engImgLink);
		spanNavSel.text("EN");
	} else if (localStorage.getItem('navRom')) {
		imgNavSel.attr("src", romImgLink);
		spanNavSel.text("RO");
	} 
	
	imgNavFra.attr("src", fraImgLink);
	imgNavEng.attr("src", engImgLink);
	imgNavRom.attr("src", romImgLink);

	$(".language").on("click", function( event ) {
		var currentId = $(this).attr('id');
		
		if (currentId == "navEng") {
			imgNavSel.attr("src", engImgLink);
			spanNavSel.text("EN");
		} else if (currentId == "navRom") {
			imgNavSel.attr("src", romImgLink);
			spanNavSel.text("RO");
		} else if (currentId == "navFra") {
			imgNavSel.attr("src", fraImgLink);
			spanNavSel.text("FR");
		}

		if (currentId == "btnEng") {
			imgBtnSel.attr("src", engImgLink);
			spanBtnSel.text("EN");
		} else if (currentId == "btnROM") {
			imgBtnSel.attr("src", romImgLink);
			spanBtnSel.text("RO");
		} else if (currentId == "btnFra") {
			imgBtnSel.attr("src", fraImgLink);
			spanBtnSel.text("FR");
		}
		
	});
});
