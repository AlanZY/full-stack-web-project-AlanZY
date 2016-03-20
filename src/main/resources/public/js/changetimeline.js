
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


function change_color()
{
    var select=document.getElementById("select_color");
    var color=select.selectedIndex;
    var set=document.getElementById("sample");
    if(color===0)
    {
        set.setAttribute('style','background-color:#FFC1C1');
    }
    else if(color===1)
    {
        set.setAttribute('style','background-color:#BFEFFF');
    }
    else if(color===2)
    {
        set.setAttribute('style','background-color:#FFFF00');
    }
    else
    {
        set.setAttribute('style','background-color:#EE7AE9');
    }
}



//set text
var uniqueId = function() {
  return 'id-' + Math.random().toString(36).substr(2, 16);
};

function addItem()
{

    var div_content=document.createElement('div');
    var div_c_id=uniqueId();
    div_content.setAttribute('id',div_c_id);
    div_content.setAttribute('class','cd-timeline-content');

    var div_block=document.createElement('div');
    div_b_id=uniqueId();
    div_block.setAttribute('id',div_b_id);
    div_block.setAttribute('class','cd-timeline-block');

    var titleToAdd=document.getElementById("change-event");
    var para_title=document.createElement('h2');
    var paraId=uniqueId();
    para_title.setAttribute('id',paraId);
    var node_title=document.createTextNode(titleToAdd.value);
    para_title.appendChild(node_title);

    var contentToAdd = document.getElementById("change-content");
    var para_content=document.createElement('p');
    var paraId_content=uniqueId();
    para_content.setAttribute('id',paraId_content);
    var node_content=document.createTextNode(contentToAdd.value);
    para_content.appendChild(node_content);

    var para_a=document.createElement('a');
    var paraa_Id=uniqueId();
    para_a.setAttribute('id',paraa_Id);
    para_a.setAttribute('href',"#");
    para_a.setAttribute('class','cd-read-more');
    para_a.setAttribute('onclick','show_change(event,"div_timeline");');
    var a_content=document.createTextNode('edit');
    para_a.appendChild(a_content);

   div_content.appendChild(para_title);
   div_content.appendChild(para_content);
   div_content.appendChild(para_a);


// color
   var select=document.getElementById("select_color");
   var color=select.selectedIndex;
   var image=document.createElement('div');
   var image_Id=uniqueId();
   image.setAttribute('id',image_Id);
   if(color===0)
   {
       image.setAttribute('style','background-color:#FFC1C1');
   }
   else if(color===1)
   {
       image.setAttribute('style','background-color:#BFEFFF');
   }
   else if(color===2)
   {
       image.setAttribute('style','background-color:#FFFF00');
   }
   else
   {
       image.setAttribute('style','background-color:#EE7AE9');
   }
  image.setAttribute('class','cd-timeline-img cd-picture');

  div_block.appendChild(image);
  div_block.appendChild(div_content);


  var add=document.getElementById('section');
  add.appendChild(div_block);








}




function Edit(id)
{
  var s;
  s=document.getElementById(id).parentElement.id;
  alert(s);
}
