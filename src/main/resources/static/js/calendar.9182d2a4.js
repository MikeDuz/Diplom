(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["calendar"],{"0a91":function(t,e,a){"use strict";a("cfaa")},a2d6:function(t,e,a){"use strict";a.r(e);var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("main",{staticClass:"Calendar Wrapper"},[a("BaseNavbar",{attrs:{className:"Calendar-Nav",navItems:t.years,activeNavIndex:t.activeYearIndex}}),a("CalendarTable",{attrs:{year:t.year,posts:t.posts}})],1)},r=[],o=a("5530"),s=(a("d3b7"),a("3ca3"),a("ddb0"),a("99af"),a("d81d"),a("c740"),a("2f62")),c=a("ed08"),i=a("bc3a"),u=a.n(i),d=a("8c89"),f=function(){return a.e("baseNavbar").then(a.bind(null,"c8ce"))},l=function(){return a.e("calendarTable").then(a.bind(null,"8691"))},p={name:"Calendar",components:{BaseNavbar:f,CalendarTable:l},data:function(){return{activeYearIndex:0,years:[],year:(new Date).getFullYear(),posts:{},errors:[]}},computed:Object(o["a"])({},Object(s["mapGetters"])(["blogInfo"])),watch:{$route:function(){this.getPostsCount()}},beforeRouteUpdate:function(t,e,a){this.year=+t.params.year,a()},methods:{getPostsCount:function(){var t=this;return u.a.get("".concat(d["SERVER_URL"],"/api/calendar?year=").concat(this.year)).then((function(e){Object(c["handleResponseErrors"])(e)||(t.years=e.data.years.map((function(t){return{name:String(t),value:String(t)}})),t.posts=e.data.posts)})).catch((function(e){t.errors.push(e)}))}},mounted:function(){var t=this;this.year=+this.$route.params.year,this.getPostsCount().then((function(){t.activeYearIndex=t.years.findIndex((function(e){return e.value==t.$route.params.year}))}))},metaInfo:function(){return{title:this.blogInfo?"Календарь | ".concat(this.blogInfo.title," - ").concat(this.blogInfo.subtitle):"Календарь"}}},b=p,h=(a("0a91"),a("2877")),v=Object(h["a"])(b,n,r,!1,null,null,null);e["default"]=v.exports},c740:function(t,e,a){"use strict";var n=a("23e7"),r=a("b727").findIndex,o=a("44d2"),s="findIndex",c=!0;s in[]&&Array(1)[s]((function(){c=!1})),n({target:"Array",proto:!0,forced:c},{findIndex:function(t){return r(this,t,arguments.length>1?arguments[1]:void 0)}}),o(s)},cfaa:function(t,e,a){},d81d:function(t,e,a){"use strict";var n=a("23e7"),r=a("b727").map,o=a("1dde"),s=o("map");n({target:"Array",proto:!0,forced:!s},{map:function(t){return r(this,t,arguments.length>1?arguments[1]:void 0)}})}}]);
//# sourceMappingURL=calendar.9182d2a4.js.map