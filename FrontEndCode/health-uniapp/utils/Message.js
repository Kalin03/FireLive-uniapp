const message = {
    error: (msg, callback) => {
        uni.showToast({
            title: msg.toString(), // 将msg转换为字符串
            icon: "none",
            success: callback ? callback : null
        })
    },
    success: (msg, callback) => {
        uni.showToast({
            title: msg.toString(), // 将msg转换为字符串
            icon: "success",
            success: callback ? callback : null
        })
    },
    warning: (msg, callback) => {
        uni.showToast({
            title: msg.toString(), // 将msg转换为字符串
            icon: "none",
            success: callback ? callback : null
        })
    }
}

export default message;
