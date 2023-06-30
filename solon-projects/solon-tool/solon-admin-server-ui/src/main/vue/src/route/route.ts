import {RouteRecordRaw} from "vue-router";

import Home from "../pages/Home.vue";
import NotFound from "../pages/NotFound.vue";
import Application from "../pages/application/index.vue";
import Details from "../pages/application/Details.vue";
import Sidebar from "../pages/application/Sidebar.vue";

export const routes: RouteRecordRaw[] = [
    {
        path: '/',
        name: 'home',
        component: Home
    },
    {
        path: '/application/name/:name/baseUrl/:baseUrl',
        name: 'application',
        components: {
            default: Application,
            sidebar: Sidebar
        },
        children: [
            {
                name: 'details',
                path: '',
                component: Details,
                meta: {
                    showInHeader: true,
                    ignored: true,
                    index: 1
                }
            }
        ],
        meta: {
            showInHeader: false,
            showSideBar: true,
        }
    },
    {
        path: '/:pathMatch(.*)*',
        name: 'NotFound',
        component: NotFound,
        meta: {
            showInHeader: false
        }
    },
]