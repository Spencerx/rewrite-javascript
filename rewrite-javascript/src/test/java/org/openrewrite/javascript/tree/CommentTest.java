/*
 * Copyright 2024 the original author or authors.
 * <p>
 * Licensed under the Moderne Source Available License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://docs.moderne.io/licensing/moderne-source-available-license
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openrewrite.javascript.tree;

import org.junit.jupiter.api.Test;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.javascript.Assertions.javaScript;

@SuppressWarnings("JSUnusedLocalSymbols")
class CommentTest implements RewriteTest {

    @Test
    void singleLineComment() {
        rewriteRun(
          javaScript(
            """
              class Test {
                  // C1
              }
              """
          )
        );
    }

    @Test
    void multilineNestedInsideSingleLine() {
        rewriteRun(
          javaScript(
            """
              class Test { // /*
              }
              """
          )
        );
    }

    @Test
    void multilineComment() {
        rewriteRun(
          javaScript(
            """
              class Test {
                  /*
                    C1
                   */
              }
              """
          )
        );
    }

    @Test
    void preserveWhitespaceBetweenComments() {
        rewriteRun(
          javaScript(
            """
              class Test {
                  /*
                    C1
                   */
              
              
              
                  /*
                      C2
                   */
              }
              """
          )
        );
    }

    @Test
    void javaScriptDoc() {
        rewriteRun(
          javaScript(
            """
              var s ;
              
              /**
               * @param {string} p1
               * @param {string} p2
               */
              function f ( p1 , p2 ) {
              }
              """
          )
        );
    }

    @Test
    void leadingComment() {
        rewriteRun(
          javaScript(
            """
              /*
               *
               */
              class Foo {}
              """
          )
        );
    }

    @Test
    void stringTemplateSingleSpanWithHead() {
        rewriteRun(
          javaScript(
            """
              function foo ( group : string) {
                  console . log ( `group: ${ /* C1 */ group /* C2 */ }` )
              }
              """
          )
        );
    }
}
