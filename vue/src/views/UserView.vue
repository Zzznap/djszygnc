<template>
    <div>
        <router-view></router-view>
    </div>
</template>

<script>
import login from '../components/userComponents/VueLogin.vue'
import register from '../components/userComponents/VueRegister.vue'

export default {
    comments:{
        login,
        register
    }
}
</script>