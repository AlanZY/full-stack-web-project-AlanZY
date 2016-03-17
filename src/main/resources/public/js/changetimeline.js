
var url = '#';
function show_change(evt,div_timeline){

if (evt.stopPropagation)    evt.stopPropagation();
 if (evt.cancelBubble!==null) evt.cancelBubble = true;
document.getElementById('div_timeline').style.display = "";

}
function hide(div_timeline){
document.getElementById('div_timeline').style.display = "none";
window.location = url;
}
document.onclick=function()
{hide('div2_timeline');
};
