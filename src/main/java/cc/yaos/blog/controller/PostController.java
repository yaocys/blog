package cc.yaos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yao 2022/9/24
 */
@Controller
public class PostController {
    @RequestMapping("/post")
    public String selectPost(ModelMap modelMap){
        String introduction = "> 导语：这是对文章内容的整体摘要，同时也是显示在预览卡片的文本内容\n";
        String sampleText ="# 这是一段示例文本内容\n"+
                "#### 这是图床加载来的图片\n"+
                "![图片描述](https://segmentfault.com/img/bVSAA2?w=825&h=431)\n"+
                "# Welcome\n" +
                "\n" +
                "Thank you for choosing **Typora**. This document will help you to start Typora. Please note that Typora for Windows is still in beta phase, so this document may be updated in future version-ups.\n" +
                "\n" +
                "[TOC]\n" +
                "\n" +
                "## Markdown For Typora\n" +
                "\n" +
                "**Typora** is using [GitHub Flavored Markdown](https://help.github.com/articles/github-flavored-markdown/) . \n" +
                "\n" +
                "To see full markdown Syntax references and extra usage, please check `Help`->`Markdown Reference` in menu bar or `About` panel. \n" +
                "\n" +
                "## Shortcut Keys\n" +
                "\n" +
                "You could find shortcut keys in the right side of menu items from menu bar. For more shortcut key details and custom key bindings, you can refer [here](http://support.typora.io/Shortcut-Keys/).\n" +
                "\n" +
                "## Copy\n" +
                "\n" +
                "To **copy Markdown source code** explicitly, please use shortcut key `shift+cmd+c` or `Copy as Markdown` from menu. To **Copy as HTML Code**, please select `Copy as HTML Code` from menu.\n" +
                "\n" +
                "## Smart Paste\n" +
                "\n" +
                "**Typora** is able to analyze styles of the text content in your clipboard when pasting. For example, after pasting a `<h1>HEADING</h1>` from some website, typora will keep the 'first level heading’ format instead of paste ‘heading’ as plain text. \n" +
                "\n" +
                "To **paste as markdown source** or plain text, you should use `paste as plain text` or press the shortcut key: `shift+cmd+v`.\n" +
                "\n" +
                "## Themes\n" +
                "\n" +
                "Please refer to `Help` → `Custom Themes` from menu bar.\n" +
                "\n" +
                "## Publish\n" +
                "\n" +
                "Currently Typora only support to export as **PDF** or **HTML**. More data format support as import/export will be integrated in future.\n" +
                "\n" +
                "## More Useful Tips & Documents\n" +
                "\n" +
                "<https://support.typora.io/>\n" +
                "\n" +
                "## And More ?\n" +
                "\n" +
                "For more questions or feedbacks, please contact us by:\n" +
                "\n" +
                "- Home Page: http://typora.io\n" +
                "- Email: <hi@typora.io>";

        String codeSnippet = "```java\npackage exam;\n" +
                "\n" +
                "import java.util.Scanner;\n" +
                "\n" +
                "/**\n" +
                " * @author yao 2022/9/13\n" +
                " */\n" +
                "public class Main {\n" +
                "    void doSomething () {\n" +
                "        String s = \"\";\n" +
                "        int l = s.length();\n" +
                "    }\n" +
                "    public static void main(String[] args) {\n" +
                "        int i = 1;\n" +
                "        System.out.println(i+'b'+1);\n" +
                "    }\n" +
                "}\n```";
        modelMap.put("sampleText",sampleText+"\n"+codeSnippet);
        modelMap.put("introduction",introduction);
        modelMap.put("postDate","2022-9-28");
        return "/site/post";
    }
}
