/* =========================================================
   MENU MOBILE
   ========================================================= */

const menuToggle = document.getElementById("menuToggle");
const mainNav = document.getElementById("mainNav");

if (menuToggle && mainNav) {

    menuToggle.addEventListener("click", () => {

        mainNav.classList.toggle("open");
        menuToggle.classList.toggle("open");

    });

}


/* =========================================================
   FECHAR MENU AO CLICAR NUM LINK
   ========================================================= */

const navLinks =
    document.querySelectorAll(".main-nav a");

navLinks.forEach(link => {

    link.addEventListener("click", () => {

        if (mainNav) {

            mainNav.classList.remove("open");

        }

        if (menuToggle) {

            menuToggle.classList.remove("open");

        }

    });

});


/* =========================================================
   HEADER AO FAZER SCROLL
   ========================================================= */

const header =
    document.querySelector(".site-header");

function atualizarHeader() {

    if (!header) return;

    if (window.scrollY > 30) {

        header.classList.add("scrolled");

    } else {

        header.classList.remove("scrolled");

    }

}

window.addEventListener(
    "scroll",
    atualizarHeader
);

atualizarHeader();


/* =========================================================
   NAVEGAÇÃO ACTIVA
   ========================================================= */

const sections =
    document.querySelectorAll("main section[id]");

const links =
    document.querySelectorAll(".nav-link");

function actualizarNavActiva() {

    let actual = "";

    sections.forEach(section => {

        const sectionTop =
            section.offsetTop - 180;

        const sectionHeight =
            section.offsetHeight;

        if (
            window.scrollY >= sectionTop &&
            window.scrollY < sectionTop + sectionHeight
        ) {

            actual = section.id;

        }

    });


    links.forEach(link => {

        link.classList.remove("active");

        const destino =
            link.getAttribute("href");

        if (destino === "#" + actual) {

            link.classList.add("active");

        }

    });

}

window.addEventListener(
    "scroll",
    actualizarNavActiva
);

actualizarNavActiva();


/* =========================================================
   ANIMAÇÕES DE SCROLL
   ========================================================= */

const elementosReveal =
    document.querySelectorAll(".reveal");

if ("IntersectionObserver" in window) {

    const observer =
        new IntersectionObserver(

            (entries) => {

                entries.forEach(entry => {

                    if (entry.isIntersecting) {

                        entry.target.classList.add(
                            "visible"
                        );

                        observer.unobserve(
                            entry.target
                        );

                    }

                });

            },

            {
                threshold: 0.12
            }

        );


    elementosReveal.forEach(elemento => {

        observer.observe(elemento);

    });

} else {

    elementosReveal.forEach(elemento => {

        elemento.classList.add("visible");

    });

}


/* =========================================================
   ANIMAÇÃO DOS CARDS COM PEQUENO ATRASO
   ========================================================= */

const gruposAnimados = [

    ".services-grid .service-card",

    ".projects-grid .project-card",

    ".process-grid .process-item"

];

gruposAnimados.forEach(selector => {

    const elementos =
        document.querySelectorAll(selector);

    elementos.forEach((elemento, index) => {

        elemento.style.transitionDelay =
            `${index * 80}ms`;

    });

});


/* =========================================================
   EFEITO 3D NOS CARDS
   ========================================================= */

const cards =
    document.querySelectorAll(
        ".service-card, .project-card"
    );

cards.forEach(card => {

    card.addEventListener(
        "mousemove",
        event => {

            if (window.innerWidth <= 800) {

                return;

            }

            const rect =
                card.getBoundingClientRect();

            const x =
                event.clientX - rect.left;

            const y =
                event.clientY - rect.top;

            const centerX =
                rect.width / 2;

            const centerY =
                rect.height / 2;

            const rotateX =
                ((y - centerY) / centerY) * -2.5;

            const rotateY =
                ((x - centerX) / centerX) * 2.5;

            card.style.transform =
                `perspective(900px)
                 rotateX(${rotateX}deg)
                 rotateY(${rotateY}deg)
                 translateY(-5px)`;

        }
    );


    card.addEventListener(
        "mouseleave",
        () => {

            card.style.transform = "";

        }
    );

});


/* =========================================================
   PARALLAX SUAVE NO HERO
   ========================================================= */

const hero =
    document.querySelector(".hero");

const heroLight =
    document.querySelector(".hero-light");

if (hero && heroLight) {

    window.addEventListener(
        "scroll",
        () => {

            if (window.innerWidth <= 800) {

                return;

            }

            const scroll =
                window.scrollY;

            heroLight.style.transform =
                `translateY(${scroll * 0.12}px)`;

        }
    );

}


/* =========================================================
   MOVIMENTO DOS ORBES COM O RATO
   ========================================================= */

const orbs =
    document.querySelectorAll(
        ".ambient-orb"
    );

document.addEventListener(
    "mousemove",
    event => {

        if (window.innerWidth <= 800) {

            return;

        }

        const x =
            (
                event.clientX /
                window.innerWidth -
                0.5
            ) * 25;

        const y =
            (
                event.clientY /
                window.innerHeight -
                0.5
            ) * 25;


        orbs.forEach((orb, index) => {

            const factor =
                (index + 1) * 0.3;

            orb.style.marginLeft =
                `${x * factor}px`;

            orb.style.marginTop =
                `${y * factor}px`;

        });

    }
);


/* =========================================================
   FORMULÁRIO DE CONTACTO (index.html)
   ========================================================= */

const contactForm =
    document.getElementById("contactForm");

const formMessage =
    document.getElementById("formMessage");

if (contactForm) {

    contactForm.addEventListener(
        "submit",
        async function (event) {

            event.preventDefault();


            /* -------------------------------------------------
               RECOLHER DADOS DO FORMULÁRIO
               ------------------------------------------------- */

            const nome =
                document
                    .getElementById("nome")
                    ?.value
                    .trim();

            const email =
                document
                    .getElementById("email")
                    ?.value
                    .trim();

            const telefone =
                document
                    .getElementById("telefone")
                    ?.value
                    .trim();

            const servico =
                document
                    .getElementById("servico")
                    ?.value;

            const mensagem =
                document
                    .getElementById("mensagem")
                    ?.value
                    .trim();


            /* -------------------------------------------------
               VALIDAR CAMPOS OBRIGATÓRIOS
               ------------------------------------------------- */

            if (
                !nome ||
                !email ||
                !servico ||
                !mensagem
            ) {

                if (formMessage) {

                    formMessage.textContent =
                        "Por favor, preencha todos os campos obrigatórios.";

                    formMessage.className =
                        "form-message error";

                }

                return;

            }


            /* -------------------------------------------------
               BOTÃO DE ENVIO
               ------------------------------------------------- */

            const botao =
                contactForm.querySelector(
                    "button[type='submit']"
                );

            const textoOriginal =
                botao
                    ? botao.innerHTML
                    : "";


            if (botao) {

                botao.disabled = true;

                botao.innerHTML =
                    "A enviar...";

            }


            /* -------------------------------------------------
               MENSAGEM DE PROCESSAMENTO
               ------------------------------------------------- */

            if (formMessage) {

                formMessage.textContent =
                    "A enviar o seu pedido...";

                formMessage.className =
                    "form-message";

            }


            /* -------------------------------------------------
               ENVIAR PARA SPRING BOOT
               ------------------------------------------------- */

            try {

                const resposta =
                    await fetch(
                        "/api/pedidos",
                        {

                            method: "POST",

                            headers: {

                                "Content-Type":
                                    "application/json"

                            },

                            body: JSON.stringify({

                                nome: nome,

                                email: email,

                                telefone: telefone,

                                servico: servico,

                                descricao: mensagem

                            })

                        }
                    );


                /* -------------------------------------------------
                   LER RESPOSTA DO SERVIDOR
                   ------------------------------------------------- */

                const texto =
                    await resposta.text();

                let dados = texto;


                try {

                    dados =
                        texto
                            ? JSON.parse(texto)
                            : {};

                } catch (erro) {

                    dados = texto;

                }


                /* -------------------------------------------------
                   VERIFICAR ERRO
                   ------------------------------------------------- */

                if (!resposta.ok) {

                    let mensagemErro =
                        "Não foi possível enviar o pedido.";

                    if (
                        typeof dados === "string" &&
                        dados.trim() !== ""
                    ) {

                        mensagemErro =
                            dados;

                    } else if (
                        dados &&
                        dados.message
                    ) {

                        mensagemErro =
                            dados.message;

                    }

                    throw new Error(
                        mensagemErro
                    );

                }


                /* -------------------------------------------------
                   SUCESSO
                   ------------------------------------------------- */

                if (formMessage) {

                    formMessage.textContent =
                        "Pedido enviado com sucesso! Entraremos em contacto consigo brevemente.";

                    formMessage.className =
                        "form-message success";

                }


                /* -------------------------------------------------
                   LIMPAR FORMULÁRIO
                   ------------------------------------------------- */

                contactForm.reset();


            } catch (erro) {

                console.error(
                    "Erro ao enviar pedido:",
                    erro
                );


                if (formMessage) {

                    formMessage.textContent =
                        erro.message ||
                        "Ocorreu um erro ao enviar o pedido. Tente novamente.";

                    formMessage.className =
                        "form-message error";

                }


            } finally {

                /* -------------------------------------------------
                   RESTAURAR BOTÃO
                   ------------------------------------------------- */

                if (botao) {

                    botao.disabled = false;

                    botao.innerHTML =
                        textoOriginal;

                }

            }

        }
    );

}


/* =========================================================
   FORMULÁRIO DE SOLICITAÇÃO (solicitar.html)
   ========================================================= */

const requestForm =
    document.getElementById("requestForm");

const requestMessage =
    document.getElementById("formMessage");

if (requestForm) {

    requestForm.addEventListener(
        "submit",
        async function (event) {

            event.preventDefault();


            /* -------------------------------------------------
               RECOLHER TODOS OS CAMPOS
               ------------------------------------------------- */

            const dados = {

                nome:
                    document
                        .getElementById("nome")
                        ?.value
                        .trim(),

                telefone:
                    document
                        .getElementById("telefone")
                        ?.value
                        .trim(),

                email:
                    document
                        .getElementById("email")
                        ?.value
                        .trim(),

                empresa:
                    document
                        .getElementById("empresa")
                        ?.value
                        .trim(),

                servico:
                    document
                        .getElementById("servico")
                        ?.value,

                titulo:
                    document
                        .getElementById("titulo")
                        ?.value
                        .trim(),

                descricao:
                    document
                        .getElementById("descricao")
                        ?.value
                        .trim(),

                orcamento:
                    document
                        .getElementById("orcamento")
                        ?.value,

                prazo:
                    document
                        .getElementById("prazo")
                        ?.value,

                observacoes:
                    document
                        .getElementById("observacoes")
                        ?.value
                        .trim(),

                aceitar:
                    document
                        .getElementById("aceitar")
                        ?.checked

            };


            /* -------------------------------------------------
               VALIDAR CAMPOS OBRIGATÓRIOS
               ------------------------------------------------- */

            if (
                !dados.nome ||
                !dados.telefone ||
                !dados.servico ||
                !dados.titulo ||
                !dados.descricao ||
                !dados.aceitar
            ) {

                if (requestMessage) {

                    requestMessage.textContent =
                        "Por favor, preencha todos os campos obrigatórios.";

                    requestMessage.className =
                        "form-message error";

                }

                return;

            }


            /* -------------------------------------------------
               BOTÃO DE ENVIO
               ------------------------------------------------- */

            const botao =
                requestForm.querySelector(
                    "button[type='submit']"
                );

            const textoOriginal =
                botao
                    ? botao.innerHTML
                    : "";


            if (botao) {

                botao.disabled = true;

                botao.innerHTML =
                    "A enviar...";

            }


            /* -------------------------------------------------
               MENSAGEM DE PROCESSAMENTO
               ------------------------------------------------- */

            if (requestMessage) {

                requestMessage.textContent =
                    "A enviar a sua solicitação...";

                requestMessage.className =
                    "form-message";

            }


            /* -------------------------------------------------
               ENVIAR PARA SPRING BOOT
               ------------------------------------------------- */

            try {

                const resposta =
                    await fetch(
                        "/api/pedidos",
                        {

                            method: "POST",

                            headers: {

                                "Content-Type":
                                    "application/json"

                            },

                            body: JSON.stringify(dados)

                        }
                    );


                const texto =
                    await resposta.text();

                let resultado = texto;


                try {

                    resultado =
                        texto
                            ? JSON.parse(texto)
                            : {};

                } catch (erro) {

                    resultado = texto;

                }


                /* -------------------------------------------------
                   VERIFICAR ERRO
                   ------------------------------------------------- */

                if (!resposta.ok) {

                    let mensagemErro =
                        "Não foi possível enviar a solicitação.";

                    if (
                        typeof resultado === "string" &&
                        resultado.trim() !== ""
                    ) {

                        mensagemErro =
                            resultado;

                    } else if (
                        resultado &&
                        resultado.message
                    ) {

                        mensagemErro =
                            resultado.message;

                    }

                    throw new Error(
                        mensagemErro
                    );

                }


                /* -------------------------------------------------
                   SUCESSO
                   ------------------------------------------------- */

                if (requestMessage) {

                    requestMessage.textContent =
                        "Solicitação enviada com sucesso! Entraremos em contacto consigo brevemente.";

                    requestMessage.className =
                        "form-message success";

                }

                requestForm.reset();


            } catch (erro) {

                console.error(
                    "Erro ao enviar solicitação:",
                    erro
                );


                if (requestMessage) {

                    requestMessage.textContent =
                        erro.message ||
                        "Ocorreu um erro ao enviar a solicitação. Tente novamente.";

                    requestMessage.className =
                        "form-message error";

                }


            } finally {

                if (botao) {

                    botao.disabled = false;

                    botao.innerHTML =
                        textoOriginal;

                }

            }

        }
    );

}


/* =========================================================
   ANO AUTOMÁTICO
   ========================================================= */

const currentYear =
    document.getElementById(
        "currentYear"
    );

if (currentYear) {

    currentYear.textContent =
        new Date().getFullYear();

}


/* =========================================================
   EFEITO DE DIGITAÇÃO NO TERMINAL
   ========================================================= */

const terminalText =
    document.querySelector(
        ".terminal span:nth-child(2)"
    );

if (terminalText) {

    const mensagens = [

        "Sistema iniciado com sucesso...",

        "Spring Boot executado...",

        "MySQL conectado...",

        "API pronta para receber pedidos...",

        "Solução digital online..."

    ];

    let indiceMensagem = 0;


    function mudarMensagemTerminal() {

        terminalText.style.opacity =
            "0";


        setTimeout(() => {

            terminalText.textContent =
                mensagens[indiceMensagem];

            terminalText.style.opacity =
                "1";

            indiceMensagem++;


            if (
                indiceMensagem >=
                mensagens.length
            ) {

                indiceMensagem = 0;

            }

        }, 300);

    }


    setInterval(
        mudarMensagemTerminal,
        3500
    );

}


/* =========================================================
   REDUCED MOTION
   ========================================================= */

if (
    window.matchMedia(
        "(prefers-reduced-motion: reduce)"
    ).matches
) {

    elementosReveal.forEach(elemento => {

        elemento.classList.add(
            "visible"
        );

        elemento.style.transitionDelay =
            "0s";

    });

}


/* =========================================================
   CONSOLE
   ========================================================= */

console.log(
    "%cMozVexa — Soluções Digitais",
    "font-size:20px;font-weight:bold;"
);

console.log(
    "Transformamos ideias em soluções digitais."
);