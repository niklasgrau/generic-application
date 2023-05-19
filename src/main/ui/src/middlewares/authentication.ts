import type {NavigationGuardNext, RouteLocationNormalized, RouteLocationRaw} from "vue-router";

// export default function auth(next: NavigationGuardNext, router) {
//     if (!localStorage.getItem('jwt')) {
//         return router.push({name: 'login'});
//     }
//
//     return next();
// }

// export default function checkAuth(next: NavigationGuardNext, isAuthenticated: boolean, to: RouteLocationRaw) {
//     if (isAuthenticated) {
//         next()
//     } else {
//         next('/login');
//     }
// }

export default function authenticated(to: RouteLocationNormalized, from: RouteLocationNormalized) {
    const isAuthenticated = true;

    // Avoid an infinite redirect by to.name check
    if (!isAuthenticated && to.name !== 'login') {
        // redirect the user to the login page
        return {
            name: 'login'
        }
    }
}