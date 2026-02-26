/**
 * 组员：shibowen
 * 功能：本地存储，保存/获取要播放的合集的数据
 * @type {{setVideoGroup: StorageUtils.setVideoGroup, getVideoGroup: (function(): any)}}
 */
const StorageUtils = {
    setVideoGroup: function (item) {
        localStorage.setItem('videoGroup', JSON.stringify(item));
    },
    getVideoGroup: function () {
        let r = JSON.parse(localStorage.getItem('videoGroup'));
        // localStorage.removeItem('videoGroup');
        // localStorage.setItem("videoGroup",'');
        console.log("取出数据",r);
        return r;
    }
}
// 定义 install 方法
StorageUtils.install = function (Vue) {
    Vue.prototype.$StorageUtils = StorageUtils;
}
export default StorageUtils;