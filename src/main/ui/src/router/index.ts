import {createRouter, createWebHistory} from "vue-router";
import authenticated from "@/middlewares/authentication";
import HomeView from "../views/HomeView.vue";
import LoginView from "@/views/LoginView.vue";
import RegisterView from "@/views/RegisterView.vue";
import type {BreadcrumbItem} from "@/models/BreadcrumbItem";
import {BreadcrumbItemTypeET} from "@/models/BreadcrumbItemTypeET";

/**
 * Definition of the default router.
 * @author Kevin Link
 */
const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: "/:pathMatch(.*)*",
            name: "not-found",
            redirect: "/"
        },
        {
            path: "/",
            name: "home",
            component: HomeView,
            meta: {
                breadcrumb: <Array<BreadcrumbItem>>[
                    {
                        title: "nav.home",
                        disabled: false,
                        to: "/",
                        type: BreadcrumbItemTypeET.NONE
                    }
                ]
            }
        },
        {
            path: "/login",
            name: "login",
            component: LoginView,
            meta: {
                breadcrumb: <Array<BreadcrumbItem>>[
                    {
                        title: "nav.login",
                        disabled: false,
                        to: "/login",
                        type: BreadcrumbItemTypeET.NONE
                    }
                ]
            }
        },
        {
            path: "/register",
            name: "register",
            component: RegisterView,
            meta: {
                breadcrumb: <Array<BreadcrumbItem>>[
                    {
                        title: "nav.register",
                        disabled: false,
                        to: "/register",
                        type: BreadcrumbItemTypeET.NONE
                    }
                ]
            }
        },
    ],
});

// Set up of middlewares (global, for all routes)
router.beforeEach((to, from) => {
    return authenticated(to, from);
});

export default router;