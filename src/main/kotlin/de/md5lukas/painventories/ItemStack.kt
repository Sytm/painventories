/*
 *     PainVentories - Library to create inventories in Spigot using panes
 *     Copyright (C) 2020 Lukas Planz <lukas.planz@web.de>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.md5lukas.painventories

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.*

inline fun itemStack(type: Material, init: ItemStack.() -> Unit) = ItemStack(type).apply(init)

inline fun ItemStack.meta(init: ItemMeta.() -> Unit) {
    itemMeta?.apply(init)
}

inline fun ItemStack.bannerMeta(init: BannerMeta.() -> Unit) = genericMeta(init)
inline fun ItemStack.blockStateMeta(init: BlockStateMeta.() -> Unit) = genericMeta(init)
inline fun ItemStack.bookMeta(init: BookMeta.() -> Unit) = genericMeta(init)
inline fun ItemStack.enchantmentStorageMeta(init: EnchantmentStorageMeta.() -> Unit) = genericMeta(init)
inline fun ItemStack.fireworkEffectMeta(init: FireworkEffectMeta.() -> Unit) = genericMeta(init)
inline fun ItemStack.fireworkMeta(init: FireworkMeta.() -> Unit) = genericMeta(init)
inline fun ItemStack.knowledgeBookMeta(init: KnowledgeBookMeta.() -> Unit) = genericMeta(init)
inline fun ItemStack.leatherArmorMeta(init: LeatherArmorMeta.() -> Unit) = genericMeta(init)
inline fun ItemStack.mapMeta(init: MapMeta.() -> Unit) = genericMeta(init)
inline fun ItemStack.potionMeta(init: PotionMeta.() -> Unit) = genericMeta(init)
inline fun ItemStack.skullMeta(init: SkullMeta.() -> Unit) = genericMeta(init)
inline fun ItemStack.spawnEggMeta(init: SpawnEggMeta.() -> Unit) = genericMeta(init)
inline fun ItemStack.tropicalFishBucketMeta(init: TropicalFishBucketMeta.() -> Unit) = genericMeta(init)

inline fun <reified T : ItemMeta> ItemStack.genericMeta(init: T.() -> Unit) {
    val meta = this.itemMeta
    if (meta is T) {
        meta.apply(init)
    } else {
        val className = T::class.simpleName
        throw IllegalArgumentException("Trying to modify the ItemMeta as $className, but it is not of type $className")
    }
}