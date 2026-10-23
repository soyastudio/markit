# Executable Markdown

1. Markdown is flatten AST, Executable Markdown(ExMD) can be parsed as tree-like AST by using Heading and Link;
2. ExMD files start with metadata block to declare executable component (template, schema, annotator, generator...) and index/search metadata (tags, metadata...);
3. ExMD files use FencedCodeBlock to define executable components, which usually provide information about format, contentBody, optionally provide additional information such as name, properties...
4. ExMD files use special markdown and html code block to describe the whole markdown and each of the components. Such descriptions can be easily chunked in RAG process
5. When an ExMD file is executed, the results of each components, such as data, file contents, tables, images etc. can also be displayed in the same file, but will not be stored and effect next execution.