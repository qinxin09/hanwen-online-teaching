const myEvents={
    LOGIN_SUCCESS:"loginsuccess",
    LOGIN_EXIT:"exitlogin",
    DIALOG_LOGIIN_SHOW:"showlogindialog",//NavBar.vue触发/Regist.vue触发
    FRESH_ICON:"freshicon",//NavBar.vue 绑定， uploadIcon.vue 触发
    FRESH_GROUP:"fresh-group",//UserUtis.js 触发  personalPage.vue绑定/index.vue绑定
    REQUEST_GROUP:"request-group",//personalPage.vue绑定 / index.vue绑定      groupList.vue触发
    PLAYER_DATA:"videoData",//player.vue 绑定  playPage.vue触发
    PLAYER_PAUSE:"pausePlay",//player.vue 绑定  playPage.vue触发
    COMMENT_SUCCESS:"sendcomsuccess",//CommentComponent.vue触发，PlayPage.vue绑定事件,
    SHOW_ADMIN_DIALOG:"showadmindialog",//adminUpdDialog绑定，adminList触发
    FRESH_ADMINLIST:"freshadminlist",//adminList绑定，adminUpdDialog触发
    SHOW_TAB:"showTab",
    CLICK_TAB:"clickTab",
    SWITCH_MENU_LIST:"switchMenuList",
    PERMISSION_SHOW_DIALOG:"showdialog",
    SAVE_PERMISSION:"savePermission",
    INIT_STSTEM_SUCCESS:"initststemsuccess",
    UPLOAD_ATTACHEMENT:"uploadAttachement",
    SHOW_PREVIEW:"showpreview",
}
// this.$myEvents.UPLOAD_ATTACHEMENT
myEvents.install = function(Vue){
    Vue.prototype.$myEvents = myEvents;
}
export default myEvents ;