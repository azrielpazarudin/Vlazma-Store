const inputs = document.querySelectorAll(".input-field");
      const toggle_btn = document.querySelectorAll(".toggle");
      const main = document.querySelector("main");
      const bullets = document.querySelectorAll(".bullets span");
      const images = document.querySelectorAll(".image");
      const formLogin = document.querySelector(".sign-in-form");
      // const inputEmail = formLogin.querySelector('[name="email"]');
      const inputPassword = formLogin.querySelector('[name="password"]');

      document.login.onsubmit = async (e) => {
        e.preventDefault();
        const response = await fetch("http:/localhost:8080/vlazma/auth/authentication", {
          method: "POST",
          headers: {
            "content-Type": "application/json",
          },
          body: JSON.stringify({
            email: document.login.email.value,
            password: document.login.password.value,
          }),
        });
        if (response.ok) {
          const token = await response.json();
          localStorage.setItem("token", token.token);
          console.log(response);
          alert("Berhasil Login");
          location.href = "../Vlazma/shop.html";
        } else {
          const message = await response.text();
          alert(message);
        }
      };

    //   formLogin.addEventListener("submit", (event) => {
    //     event.preventDefault();
    //     const dataLogin = {
    //       email: inputEmail.value,
    //       password: inputPassword.value,
    //     };

    //     fetch("http://localhost:8080/vlazma/product/", {
    //       method: "GET",
    //       headers: {
    //         "Content-Type": "application/json",
    //         Authorization:
    //           `Bearer ${localStorage}`,
    //       },
    //     })
    //       .then((response) => response.json())
    //       .then((data) => {
    //         console.log(data);
    //         if (data.success) {
    //           sessionStorage.setItem("token", data.token);
    //           alert("Login Berhasil");
    //         } else {
    //           console.log(response);
    //           alert("Login Gagal!");
    //         }
    //       })
    //       .catch((error) => {
    //         console.error(error);
    //         alert("Terjadi kesalahan pada server!");
    //       });
    //   });

      inputs.forEach((inp) => {
        inp.addEventListener("focus", () => {
          inp.classList.add("active");
        });
        inp.addEventListener("blur", () => {
          if (inp.value != "") return;
          inp.classList.remove("active");
        });
      });

      toggle_btn.forEach((btn) => {
        btn.addEventListener("click", () => {
          main.classList.toggle("sign-up-mode");
        });
      });

      function moveSlider() {
        let index = this.dataset.value;

        let currentImage = document.querySelector(`.img-${index}`);
        images.forEach((img) => img.classList.remove("show"));
        currentImage.classList.add("show");

        const textSlider = document.querySelector(".text-group");
        textSlider.style.transform = `translateY(${-(index - 1) * 2.2}rem)`;

        bullets.forEach((bull) => bull.classList.remove("active"));
        this.classList.add("active");
      }

      bullets.forEach((bullet) => {
        bullet.addEventListener("click", moveSlider);
      });