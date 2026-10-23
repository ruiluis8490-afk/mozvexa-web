# Usa uma imagem oficial do Java 17
FROM eclipse-temurin:17-jdk-alpine

# Define o diretorio de trabalho
WORKDIR /app

# Copia todos os ficheiros do projeto para dentro do container
COPY . .

# Dá permissao de execução ao Maven Wrapper e compila o projeto
RUN chmod +x mvnw && ./mvnw clean package -DskipTests

# Copia o JAR gerado para um nome fixo (app.jar)
# O log mostrou que o ficheiro e RuiLuisSolucoesDigitais-0.0.1-SNAPSHOT.jar
RUN cp target/RuiLuisSolucoesDigitais-0.0.1-SNAPSHOT.jar app.jar

# Expoe a porta 8080
EXPOSE 8080

# Comando para iniciar a aplicacao usando o nome fixo
ENTRYPOINT ["java", "-jar", "app.jar"]