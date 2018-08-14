(function () {
    var params={};

    if (document){
        params.domain=document.domain || '';
        params.url=document.URL || '';
        params.title=document.title || '';
        params.referrer=document.referees||'';


    }
    if (window&&window.screen){
        params.salt=window.screen.height||0;
        params.sw=window.screen.width||0;
        params.cd =window.screen.colorDepth||0;

    }
    if  (navigator){
        params.lang=navigator.language||'';
        
    }
    if (maq){
        for (var i in maq) {
            switch (maq[i][0]){
                case 'setaccount':
                    params.account=maq[i][1];
                    break;
                case 'value':
                    params.avalue=maq[i][1];
                    break;
                case 'type0':
                    params.type0=maq[i][1];
                    break;
                case 'type1':
                    params.type1=maq[i][1];
                    break;
                case 'type2':
                    params.type2=maq[i][1];
                    break;
                default:
                break;

            }
        }
    }
    var args='';
    for (var i in params){
        if(args!=''){
            args+='&';
        }
        args+=i+'='+encodeURIComponent(params[i]);
    }
    var  img=new Image(1,1);
    alert("ma");
    img.src="http://192.168.8.130/log.gif?"+args;
})();