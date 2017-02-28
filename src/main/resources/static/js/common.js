/**
 * 共通で使用するJavaScript Function
 */

/**
 * GETのAjax処理を実行します.
 * Modalの結果など、HTMLを結果として取得します.
 *
 * @param  url URL
 * @param  data 引数オブジェクト
 * @return jQuery XMLHttpRequest.
 */
function getHtmlAjax(url, data) {

    return $.ajax({
        type: "GET",
        url: url,
        data: data,
        cache: false,
        dataType: "html"
    });
}

/**
 * Spring SecurityのCSRFヘッダを付加し,Ajax処理を実行します.
 * HTMLのheadタグにCSRFのメタ情報が必要です.
 * Modalの結果など、HTMLを結果として取得します.
 *
 * @param  url URL
 * @param  data 引数オブジェクト
 * @return jQuery XMLHttpRequest.
 */
function postHtmlCsrfAjax(url, data) {
    // CSRF用ヘッダの設定
    var header = {};
    header[$("meta[name='_csrf_header']").attr("content")] =
        $("meta[name='_csrf']").attr("content");
    return $.ajax({
        type: "POST",
        headers: header,
        url: url,
        data: data,
        cache: false,
        dataType: "html"
    });
}