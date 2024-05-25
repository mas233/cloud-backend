let api = [];
api.push({
    alias: 'api',
    order: '1',
    desc: '',
    link: '',
    list: []
})
api[0].list.push({
    order: '1',
    desc: '访问所有课程信息 并返回一个列表 将此接口映射为url',
});
api[0].list.push({
    order: '2',
    desc: '',
});
api[0].list.push({
    order: '3',
    desc: '',
});
api[0].list.push({
    order: '4',
    desc: '',
});
api[0].list.push({
    order: '5',
    desc: '',
});
api[0].list.push({
    order: '6',
    desc: '对课程进行弱模糊搜索 并返回一个列表 搜索模式：匹配关键字{name}，关键字必须完整 url:/findByName/{name}',
});
api[0].list.push({
    order: '7',
    desc: '',
});
api[0].list.push({
    order: '8',
    desc: '',
});
api[0].list.push({
    order: '9',
    desc: '',
});
api[0].list.push({
    order: '10',
    desc: '根据课程id查找课程名',
});
api[0].list.push({
    order: '11',
    desc: '',
});
api[0].list.push({
    order: '12',
    desc: '',
});
api.push({
    alias: 'LogController',
    order: '2',
    desc: '',
    link: '',
    list: []
})
api[1].list.push({
    order: '1',
    desc: '',
});
api.push({
    alias: 'ResourceHandler',
    order: '3',
    desc: '',
    link: '',
    list: []
})
api[2].list.push({
    order: '1',
    desc: '根据课程名字搜索该课程的资源 url:"/findByCoursename/{coursename}"',
});
api[2].list.push({
    order: '2',
    desc: '',
});
api[2].list.push({
    order: '3',
    desc: '',
});
api[2].list.push({
    order: '4',
    desc: '',
});
api[2].list.push({
    order: '5',
    desc: '',
});
api[2].list.push({
    order: '6',
    desc: '实现文件下载 url:"/downloadfile/{resourceId}',
});
api[2].list.push({
    order: '7',
    desc: '对资源进行弱模糊搜索 并返回一个列表 搜索模式：匹配关键字{name}，关键字必须完整 url:/findByName/{name}',
});
api.push({
    alias: 'UserHandler',
    order: '4',
    desc: '',
    link: '',
    list: []
})
api[3].list.push({
    order: '1',
    desc: '用户注册 url:"/user/register"',
});
api[3].list.push({
    order: '2',
    desc: '用户登录 url:"/user/login"',
});
api[3].list.push({
    order: '3',
    desc: '检查修改密码时，用户输入密码是否正确',
});
api[3].list.push({
    order: '4',
    desc: '用户修改密码 url:"/modify/passwd"',
});
api[3].list.push({
    order: '5',
    desc: '用户贡献度排行榜获取 URL="/user/rank"',
});
api[3].list.push({
    order: '6',
    desc: '根据用户名获取贡献值 url:"/getContrib/{username}"',
});
api[3].list.push({
    order: '7',
    desc: '',
});
api[3].list.push({
    order: '8',
    desc: '',
});
api[3].list.push({
    order: '9',
    desc: '',
});
api[3].list.push({
    order: '10',
    desc: '',
});
api[3].list.push({
    order: '11',
    desc: '',
});
api[3].list.push({
    order: '12',
    desc: '',
});
api[3].list.push({
    order: '13',
    desc: '',
});
api[3].list.push({
    order: '14',
    desc: '',
});
api[3].list.push({
    order: '15',
    desc: '',
});
api.push({
    alias: 'picture_init',
    order: '5',
    desc: '',
    link: '',
    list: []
})
document.onkeydown = keyDownSearch;
function keyDownSearch(e) {
    const theEvent = e;
    const code = theEvent.keyCode || theEvent.which || theEvent.charCode;
    if (code === 13) {
        const search = document.getElementById('search');
        const searchValue = search.value;
        let searchArr = [];
        for (let i = 0; i < api.length; i++) {
            let apiData = api[i];
            const desc = apiData.desc;
            if (desc.toLocaleLowerCase().indexOf(searchValue) > -1) {
                searchArr.push({
                    order: apiData.order,
                    desc: apiData.desc,
                    link: apiData.link,
                    alias: apiData.alias,
                    list: apiData.list
                });
            } else {
                let methodList = apiData.list || [];
                let methodListTemp = [];
                for (let j = 0; j < methodList.length; j++) {
                    const methodData = methodList[j];
                    const methodDesc = methodData.desc;
                    if (methodDesc.toLocaleLowerCase().indexOf(searchValue) > -1) {
                        methodListTemp.push(methodData);
                        break;
                    }
                }
                if (methodListTemp.length > 0) {
                    const data = {
                        order: apiData.order,
                        desc: apiData.desc,
                        alias: apiData.alias,
                        link: apiData.link,
                        list: methodListTemp
                    };
                    searchArr.push(data);
                }
            }
        }
        let html;
        if (searchValue === '') {
            const liClass = "";
            const display = "display: none";
            html = buildAccordion(api,liClass,display);
            document.getElementById('accordion').innerHTML = html;
        } else {
            const liClass = "open";
            const display = "display: block";
            html = buildAccordion(searchArr,liClass,display);
            document.getElementById('accordion').innerHTML = html;
        }
        const Accordion = function (el, multiple) {
            this.el = el || {};
            this.multiple = multiple || false;
            const links = this.el.find('.dd');
            links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown);
        };
        Accordion.prototype.dropdown = function (e) {
            const $el = e.data.el;
            let $this = $(this), $next = $this.next();
            $next.slideToggle();
            $this.parent().toggleClass('open');
            if (!e.data.multiple) {
                $el.find('.submenu').not($next).slideUp("20").parent().removeClass('open');
            }
        };
        new Accordion($('#accordion'), false);
    }
}

function buildAccordion(apiData, liClass, display) {
    let html = "";
    if (apiData.length > 0) {
         for (let j = 0; j < apiData.length; j++) {
            html += '<li class="'+liClass+'">';
            html += '<a class="dd" href="' + apiData[j].alias + '.html#header">' + apiData[j].order + '.&nbsp;' + apiData[j].desc + '</a>';
            html += '<ul class="sectlevel2" style="'+display+'">';
            let doc = apiData[j].list;
            for (let m = 0; m < doc.length; m++) {
                html += '<li><a href="' + apiData[j].alias + '.html#_' + apiData[j].order + '_' + doc[m].order + '_' + doc[m].desc + '">' + apiData[j].order + '.' + doc[m].order + '.&nbsp;' + doc[m].desc + '</a> </li>';
            }
            html += '</ul>';
            html += '</li>';
        }
    }
    return html;
}