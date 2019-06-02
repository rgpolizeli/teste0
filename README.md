# Comentários sobre o projeto Goomer Lista Rango.

Teço aqui alguns comentários gerais destacando as dificuldades enfrentadas no decorrer do projeto.

## Dificuldades

* O modelo de interface fornecido possui medidas em **px** e não em **dp**. Isto me confundiu, já que o Android Studio adverte para usar **dp**.
* Devido a pouca experiência com consumo de API, a desserialização do JSON foi meu principal deseafio. Perdi muito tempo tentando converter o JSON manualmente, só depois percebi que estava reinventando a roda e utilizei o desserialização do Gson.
* O desenvolvimento de drawables com partes transparentes demandaram esforço e, infelizmente, não deu tempo de finalizar a dialog que expõe as informações de um item do menu clicado.
* O JSON não-padronizado (por exemplo, alguns elementos sem determinados campos, como a falta de imagens e a falta de preços em alguns items do menu) ofereceu alguns desafios e exigiu código extra para tratar estes casos na hora da exibição nas views.
* O fato de os grupos das refeições do menu serem variáveis e não-padronizados tornou o processo de exibição dos items do menu bem mais complexo. E ainda havia o grupo bebidas em minúsculo :( A minha estratégia foi definir um número máximo de grupos e instanciar uma RecyclerView para cada grupo. Como não havia um padrão nos grupos, um mapeamento extra entre as recyclerViews criadas e os grupos do menu foi necessário.
* Implementar os grupos do menu em views expansívas gerou muito código extra para gerenciar a expansão/desexpansão.
* Não deu tempo de implementar a mudança do status das promoções e dos restaurantes. Creio que poderia fazer isto utilizando o WorkManager, mas não tenho experiência com o mesmo.
* Também não implementei as buscas e os botões da dialog, apenas o botão de fechamento. 

## O que melhorar

* Utilizar **dp** em vez de **px**.
* O tratamento de erros (implementei pouco ou quase nada).
* Melhorar comentários, definindo, de preferência, padrões para nomes de variáveis.
* Testes e tentar implementar o código de forma a ser testável.
* Extrair padrões de dimensão e registrá-los no arquivo values/dimens.xml.
* Registrar todas as cores e não reaproveitá-las como fiz em alguns casos.
* Utilizar Paging Library para consumir os restaurantes e items do menu em partes em vez de consumir todos de uma única vez como está sendo feito.
* A padronização do JSON  (sem campos faltantes) e a padronização de grupos simplificaria a exibição, reduziria código para tratar as exceções e código para mapear as categorias que chegam com as recyclerViews já presentes.
* Utilizar a Data Binding Library para reduzir a quantidade de código na Activity apenas para carregar as views inicialmente.
* Não trabalhar em casa com pessoas te atrapalhando :)
* Trabalhar em equipe também ajudaria bastante, alivia muito a pressão.
