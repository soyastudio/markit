package sorya.framework.markita.util;

import org.commonmark.node.Node;
import org.commonmark.node.Paragraph;
import org.commonmark.node.Text;

public class MarkdownUtils {
    public static String render(Paragraph block) {
        // FIXME:
        TextBuilder builder = TextBuilder.builder();
        Node node = block.getFirstChild();

        while (node != null ) {
            if(node instanceof Text text) {
                builder.append(text.getLiteral());
            }
            node = node.getNext();
        }

        StringBuilder stringBuilder;
        return builder.toString();
    }
}
